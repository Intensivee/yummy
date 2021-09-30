import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  loginForm: FormGroup;
  errorResponse = false;

  constructor(private formBuilder: FormBuilder) {
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

  get confirmPassword() {
    return this.loginForm.controls.confirmPassword;
  }

  initializeForm(): void {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8)])
    }, {updateOn: 'submit'});
  }

  onSubmit(): void {
    this.errorResponse = false;
    this.loginForm.markAllAsTouched();
    if (this.loginForm.valid) {
      this.register();
    }
  }

  register(): void {

  }
}
