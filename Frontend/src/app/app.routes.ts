import { Routes } from '@angular/router';
import {RouterModule, } from '@angular/router';
import {NgModule} from '@angular/core';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {FormContainerComponent} from "./form-container/form-container.component";
import { LoginComponent } from './login/login.component';
export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  {path: 'login', component: LoginComponent},
  { path: 'Form/:id', component: FormContainerComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule {
}
