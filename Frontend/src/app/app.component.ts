import { Component } from '@angular/core';
import { RouterModule} from '@angular/router';
import { CommonModule } from '@angular/common';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";
import { AppService } from './app.service';


@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone:true,
})
export class AppComponent {
  title = 'DigitalForms';

  token ='Affe';
  service1;
  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
    this.updateToken();
  
    });
 
  }
  private updateToken() {
    this.token = this.oauthService.getAccessToken();
    this.service1.start(this.token).subscribe(response => {
      this.token = response;
    });
  }
  constructor(private oauthService: OAuthService, private appService: AppService ) {
  this.configure();
  this.service1=appService;
  }
 
  testbackend(){


  console.log("startet");
  }


  login() {
    this.oauthService.initCodeFlow();
  }
 


  logout() {
    this.oauthService.logOut();
  }
}
