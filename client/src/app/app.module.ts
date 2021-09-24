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
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RecipeBlockComponent} from './components/recipe-block/recipe-block.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatFormFieldModule} from '@angular/material/form-field';
import {AngularFireStorageModule} from '@Angular/fire/storage';
import {AngularFireModule} from '@Angular/fire';
import {MatDialogModule} from '@angular/material/dialog';
import {LoginComponent} from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegisterComponent} from './components/register/register.component';
import {JwtHttpInterceptorService} from './security/jwt-http-interceptor.service';
import {UserEditComponent} from './components/user-edit/user-edit.component';
import {environment} from 'src/environments/environment';
import {EditPasswordComponent} from './components/edit-password/edit-password.component';
import {CreateRecipeComponent} from './components/create-recipe/create-recipe.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatInputModule} from '@angular/material/input';
import {AlignPipe} from './pipes/align.pipe';
import {ComponentsManageComponent} from './components/components-manage/components-manage.component';
import {MatSelectModule} from '@angular/material/select';
import {ComponentCategoryMultiSelectComponent} from './components/components-manage/component-category-multi-select/component-category-multi-select.component';
import {MatTableModule} from '@angular/material/table';
import {MatSnackBarModule} from '@angular/material/snack-bar';

const MAT_MODULES = [
  MatMenuModule,
  BrowserAnimationsModule,
  MatListModule,
  MatPaginatorModule,
  MatDialogModule,
  MatAutocompleteModule,
  MatFormFieldModule,
  MatInputModule,
  MatSelectModule,
  MatTableModule,
  MatSnackBarModule
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
    EditPasswordComponent,
    CreateRecipeComponent,
    AlignPipe,
    ComponentsManageComponent,
    ComponentCategoryMultiSelectComponent
  ],
  imports: [
    MAT_MODULES,
    AppRoutingModule,
    FormsModule,
    BrowserModule,
    RouterModule,
    FontAwesomeModule,
    HttpClientModule,
    ReactiveFormsModule,
    AngularFireStorageModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireStorageModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtHttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
