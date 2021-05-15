import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogConfig } from '@angular/material/dialog';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private router: Router,
              private dialog: MatDialog,
              private formBuilder: FormBuilder,
              public dialogRef: MatDialogRef<RegisterComponent>) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('')
    });
  }

  onSubmit(): void {
    console.log(this.loginForm);
  }

  // passwordMatchValidator() {
  //   const password = this.loginForm.controls['password'].value;
  //   const confirmPassword = this.loginForm.controls['confirmPassword'].value
  //   return password === confirmPassword ? null : { notSame: true };
  // }

  get email() { return this.loginForm.controls.email; }
  get username() { return this.loginForm.controls.username; }
  get password() { return this.loginForm.controls.password; }
  get confirmPassword() { return this.loginForm.controls.confirmPassword; }
}
