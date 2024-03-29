import {UserRoleType} from './model/UserRoleType';
import {RoleGuardService} from './security/role-guard.service';
import {UserEditComponent} from './components/user-edit/user-edit.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {RecipeComponent} from './components/recipe/recipe.component';
import {RecipesComponent} from './components/recipes/recipes.component';
import {MainComponent} from './components/main/main.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from './components/user/user.component';
import {ComponentsManageComponent} from './components/components-manage/components-manage.component';
import {ErrorPageComponent} from './components/error-page/error-page.component';

const routes: Routes = [
  {path: 'main', component: MainComponent},
  {path: 'welcome', component: WelcomeComponent},
  {path: 'user/:username', component: UserComponent},
  {path: 'user', component: UserComponent},
  {path: 'user-edit', component: UserEditComponent},
  {path: 'recipes', component: RecipesComponent},
  {path: 'recipes/byCategory/:category', component: RecipesComponent},
  {path: 'recipes/byComponent/:component', component: RecipesComponent},
  {path: 'recipes/byTimeType/:timeType', component: RecipesComponent},
  {path: 'recipes/search/:searchKey', component: RecipesComponent},
  {path: 'recipe/:id', component: RecipeComponent},
  {path: 'error', component: ErrorPageComponent},
  {
    path: 'components-menage',
    component: ComponentsManageComponent,
    canActivate: [RoleGuardService],
    data: {expectedRole: UserRoleType.ROLE_ADMIN}
  },
  {path: '', redirectTo: '/welcome', pathMatch: 'full'},
  {path: '**', redirectTo: '/main', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
