import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
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
