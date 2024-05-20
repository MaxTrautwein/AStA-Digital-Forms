import { Component } from '@angular/core';
import {CollapsableBottomComponent} from "../collapsable-bottom/collapsable-bottom.component";
import {MainButtonsComponent} from "../main-buttons/main-buttons.component";
import {OAuthService} from "angular-oauth2-oidc";


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CollapsableBottomComponent,
    MainButtonsComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  constructor(protected Auth: OAuthService) {}

}
