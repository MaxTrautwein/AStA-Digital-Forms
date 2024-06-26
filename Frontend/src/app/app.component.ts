import { Component } from '@angular/core';
import { Router, RouterModule, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";
import { AppService } from './app.service';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { CollapsableBottomComponent } from './collapsable-bottom/collapsable-bottom.component';
import { MainButtonsComponent } from './dashboard/main-buttons/main-buttons.component';
import {ApiModule, FormsService, DefaultService} from './api-client';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import {TokenService} from "./token.service";


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, RouterModule,LoginComponent, NavBarComponent, CollapsableBottomComponent, MainButtonsComponent, ApiModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone:true,

})
export class AppComponent {
  title = 'DigitalForms';
  token ='';
  service;

  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
    if(!this.oauthService.hasValidAccessToken()) {
      this.router.navigateByUrl('/login');
    }


    });

  }

  constructor(private oauthService: OAuthService, private appService: AppService, private defaultservice: DefaultService,
              private formsService: FormsService, private router :Router, private tokenService: TokenService) {
  this.configure();
  this.service=appService;
   // oauthService
  }


  login() {
    this.oauthService.initCodeFlow();
  }



  logout() {
    this.oauthService.logOut();

  }
}
