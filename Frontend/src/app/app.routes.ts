import { Routes } from '@angular/router';
import {RouterModule, } from '@angular/router';
import {NgModule} from '@angular/core';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {FormContainerComponent} from "./form-container/form-container.component";
export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'Form', component: FormContainerComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule {
}
