import {MatDialogRef} from '@angular/material/dialog';
import {AuthenticationService} from '../../security/authentication.service';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  loginForm: FormGroup;
  errorResponse: string = '';

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<RegisterComponent>,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.initializeForm();
  }

  get email() {
    return this.loginForm.controls.email;
  }

  get username() {
    return this.loginForm.controls.username;
  }

  get password() {
    return this.loginForm.controls.password;
  }

  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
        email: new FormControl('', [Validators.required, Validators.email]),
        username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]),
        password: new FormControl('', [Validators.required, Validators.minLength(8)])
      }, {updateOn: 'submit'}
    );
  }

  onSubmit(): void {
    this.errorResponse = '';
    this.loginForm.markAllAsTouched();
    if (this.loginForm.valid) {
      this.register();
    }
  }

  register(): void {
    this.authenticationService.registerUser(
      this.email.value, this.username.value, this.password.value)
      .subscribe(
        () => {
          this.router.navigate(['main']);
          this.dialogRef.close();
        }, (error) => {
          this.errorResponse = error.error.message;
        }
      );
  }
}
