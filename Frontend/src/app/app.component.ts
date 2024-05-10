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
  text = '';
  service1;
  constructor(private oauthService: OAuthService, private appService: AppService ) {
  this.configure();
  this.service1=appService;
  appService.start().subscribe(response => {
  this.text = response;
 });
  }
 
  testbackend(){

  this.service1.start();
  console.log("startet");
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
