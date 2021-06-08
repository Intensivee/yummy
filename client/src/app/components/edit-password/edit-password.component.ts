import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.css']
})
export class EditPasswordComponent implements OnInit {

  passwordForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.passwordForm = this.formBuilder.group({
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('')
    });
  }

  onSubmit(): void {
  }

  get password() { return this.passwordForm.controls.password; }
  get confirmPassword() { return this.passwordForm.controls.confirmPassword; }
}
