import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { DashboardComponent } from "./dashboard/dashboard.component";
import { FormContainerComponent } from "./form-container/form-container.component";
import { LoginComponent } from './login/login.component';
import { UserDataComponent } from './user-data/user-data.component';
import {FilledFormsComponent} from "./filled-forms/filled-forms.component";

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'login', component: LoginComponent},
  { path: 'Form/:id', component: FormContainerComponent },
  { path: 'userdata', component: UserDataComponent},
  { path: 'filledForms', component: FilledFormsComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule {
}
