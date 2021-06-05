import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {RouterModule} from '@angular/router';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NavigationComponent} from './components/navigation/navigation.component';
import {MainComponent} from './components/main/main.component';
import {RecipesComponent} from './components/recipes/recipes.component';
import {RecipeComponent} from './components/recipe/recipe.component';
import {UserComponent} from './components/user/user.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatListModule} from '@angular/material/list';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {RecipeBlockComponent} from './components/recipe-block/recipe-block.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {AngularFireStorageModule} from '@Angular/fire/storage';
import {AngularFireModule} from '@Angular/fire';
import {MatDialogModule} from '@angular/material/dialog';
import {LoginComponent} from './components/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {RegisterComponent} from './components/register/register.component';
import {JwtHttpInterceptorService} from './security/jwt-http-interceptor.service';
import {UserEditComponent} from './components/user-edit/user-edit.component';
import {environment} from 'src/environments/environment';
import { EditPasswordComponent } from './components/edit-password/edit-password.component';

const MAT_MODULES = [
  MatMenuModule,
  BrowserAnimationsModule,
  MatListModule,
  MatPaginatorModule,
  MatDialogModule
];

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    NavigationComponent,
    MainComponent,
    RecipesComponent,
    RecipeComponent,
    UserComponent,
    RecipeBlockComponent,
    LoginComponent,
    RegisterComponent,
    UserEditComponent,
    EditPasswordComponent
  ],
  imports: [
    MAT_MODULES,
    AppRoutingModule,
    BrowserModule,
    RouterModule,
    FontAwesomeModule,
    HttpClientModule,
    ReactiveFormsModule,
    AngularFireStorageModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireStorageModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtHttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
