import {LoginComponent} from '../components/login/login.component';
import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ActivatedRouteSnapshot, Router} from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService {

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private dialog: MatDialog) {
  }


  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (!this.authenticationService.isAuthenticated()) {
      this.openLoginDialog();
      return false;
    }

    const hasAuthorization = this.authenticationService.getUserRole() === route.data.expectedRole;
    if (hasAuthorization) {
      return true;
    }

    this.router.navigate(['main']);
    return false;
  }

  openLoginDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(LoginComponent, dialogConfig);
  }
}
