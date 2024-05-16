import { Component } from '@angular/core';
import { RouterModule, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";
import { AppService } from './app.service';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { CollapsableBottomComponent } from './collapsable-bottom/collapsable-bottom.component';
import { MainButtonsComponent } from './main-buttons/main-buttons.component';
import { ApiModule } from './api-client';
import { HttpClientModule } from '@angular/common/http';
import { Configuration } from './api-client';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, RouterModule, NavBarComponent, CollapsableBottomComponent, MainButtonsComponent, ApiModule, HttpClientModule],
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
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
    this.updateToken();

    });

  }
  private updateToken() {
    this.token = this.oauthService.getAccessToken();
    this.service.start(this.token).subscribe(response => {
      this.token = response;
    });
    

  }
  constructor(private oauthService: OAuthService, private appService: AppService ) {
  this.configure();
  this.service=appService;
  }

  login() {
    this.oauthService.initCodeFlow();
  }



  logout() {
    this.oauthService.logOut();
  }
}
