import { AuthenticationService } from '../../security/authentication.service';
import { RegisterComponent } from '../register/register.component';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogConfig } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  errorResponse = false;

  constructor(private router: Router,
              private dialog: MatDialog,
              private formBuilder: FormBuilder,
              public dialogRef: MatDialogRef<LoginComponent>,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }

  onSubmit(): void {
    console.log(this.loginForm);
    this.authenticationService.authenticateCredentials(
      this.loginForm.controls.email.value, this.loginForm.controls.password.value)
      .subscribe(
        () => {
          this.router.navigate(['user',  this.authenticationService.getAuthenticatedUsername()]);
        }, () => {
          this.errorResponse = true;
        }
      );
    this.errorResponse = true;
  }

  openRegisterDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(RegisterComponent, dialogConfig);
  }

  get email() { return this.loginForm.controls.email; }
  get password() { return this.loginForm.controls.password; }
}
