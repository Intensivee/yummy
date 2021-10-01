import {EditPasswordComponent} from '../edit-password/edit-password.component';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {AngularFireStorage} from '@angular/fire/storage';
import {AuthenticationService} from '../../security/authentication.service';
import {Component, OnInit} from '@angular/core';
import {User} from 'src/app/model/user';
import {DEFAULT_IMG} from 'src/app/constants';
import {finalize} from 'rxjs/operators';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  user: User;
  userForm: FormGroup;
  selectedImage = null;
  errorMessage = null;

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private fireStorage: AngularFireStorage,
              private router: Router,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.initializeForm();

    const username = this.authenticationService.getAuthenticatedUsername();
    this.userService.getByUsername(username).subscribe(data => {
      this.user = data;
      this.patchFormWithUserDetails();
    });
  }

  initializeForm(): void {
    this.userForm = this.formBuilder.group({
      imgSource: new FormControl(DEFAULT_IMG),
      bio: new FormControl('', [Validators.maxLength(200)])
    });
  }

  patchFormWithUserDetails(): void {
    this.userForm.patchValue({
      imgSource: this.user.img,
      bio: this.user.bio
    });
  }

  onImgChange(event: any): void {
    this.errorMessage = null;
    this.selectedImage = null;

    if (this.isImageValid(event)) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        this.userForm.patchValue({imgSource: e.target.result});
      };
      reader.readAsDataURL(event.target.files[0]);
      this.selectedImage = event.target.files[0];
    } else {
      this.errorMessage = 'Provide proper image that weighs less than 2MB!';
    }
  }

  isImageValid(event: any): boolean {
    const maxSizeInMB = 2;
    return (event.target.files && event.target.files[0] &&
      event.target.files[0].size / (1024 * 1024) < maxSizeInMB);
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }
    if (this.selectedImage != null) {
      this.saveUserWithImgUpload();
    } else {
      this.saveUser();
    }
  }

  saveUserWithImgUpload(): void {
    const filePath = `user-img/${this.selectedImage.name.split('.').slice(0, -1).join('.')}_${new Date().getTime()}`;
    const fileRef = this.fireStorage.ref(filePath);

    this.fireStorage.upload(filePath, this.selectedImage)
      .snapshotChanges().pipe(
      finalize(() => {
        fileRef.getDownloadURL().subscribe((url) => {
          this.userForm.patchValue({imgSource: url});
          this.saveUser();
        });
      })
    ).subscribe();
  }

  saveUser(): void {
    const user = this.mapFormToUser();
    this.userService.editUser(user).subscribe(
      () => {
        this.router.navigate(['/user']);
      }, () => {
        this.clearAllFields();
        this.errorMessage = 'Could not edit details';
      }
    );
  }

  mapFormToUser(): User {
    const user = new User();
    user.img = this.userForm.controls.imgSource.value;
    user.bio = this.userForm.controls.bio.value;
    return user;
  }

  clearAllFields(): void {
    this.patchFormWithUserDetails();
    if (this.selectedImage != null) {
      this.removeImgFromFireStorage();
    }
  }

  removeImgFromFireStorage(): void {
    this.fireStorage.refFromURL(this.userForm.controls.imgSource.value).delete();
  }

  openPasswordEditPopup(): void {
    this.dialog.open(EditPasswordComponent);
  }

  get imgSource() {
    return this.userForm.controls.imgSource;
  }

  get bio() {
    return this.userForm.controls.bio;
  }
}
