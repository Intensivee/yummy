import { RegisterComponent } from '../register/register.component';
import { LoginComponent } from '../login/login.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openLoginDialog(): void {
    this.dialog.open(LoginComponent, this.getDialogConfiguration());
  }

  openRegisterDialog(): void {
    this.dialog.open(RegisterComponent, this.getDialogConfiguration());
  }

  private getDialogConfiguration(): MatDialogConfig {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    return dialogConfig;
  }
}
