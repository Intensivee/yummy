import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavigationComponent } from './components/navigation/navigation.component';
import { MainComponent } from './components/main/main.component';
import { RecipesComponent } from './components/recipes/recipes.component';
import { RecipeComponent } from './components/recipe/recipe.component';
import { UserComponent } from './components/user/user.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RecipeBlockComponent } from './components/recipe-block/recipe-block.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { AngularFireStorageModule } from '@Angular/fire/storage';
import { AngularFireModule } from '@Angular/fire';

const MAT_MODULES = [
  MatMenuModule,
  BrowserAnimationsModule,
  MatListModule,
  MatPaginatorModule
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
    RecipeBlockComponent
  ],
  imports: [
    MAT_MODULES,
    AppRoutingModule,
    BrowserModule,
    RouterModule,
    FontAwesomeModule,
    HttpClientModule,
    AngularFireStorageModule,
    AngularFireModule.initializeApp({
      apiKey: 'AIzaSyDN_CBrVbRsBgsnRDNh_jsub74J1wL6zoE',
      authDomain: 'yummy-b2c0e.firebaseapp.com',
      projectId: 'yummy-b2c0e',
      storageBucket: 'yummy-b2c0e.appspot.com',
      messagingSenderId: '1044823503985',
      appId: '1:1044823503985:web:8bd6482c1c388a2a3857d0',
      measurementId: 'G-B48CWF6LE9'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
