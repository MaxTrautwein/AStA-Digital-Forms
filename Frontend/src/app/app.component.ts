import { Component } from '@angular/core';
import { RouterModule} from '@angular/router';
import { CommonModule } from '@angular/common';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";


@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone:true,
})
export class AppComponent {
  title = 'DigitalForms';
  text = '';
  
  constructor(private oauthService: OAuthService) {
  this.configure();
  }
  
  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  login() {
    this.oauthService.initCodeFlow();
  }
 


  logout() {
    this.oauthService.logOut();
  }
}
