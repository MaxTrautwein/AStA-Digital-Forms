import { Component } from '@angular/core';
import { Router, RouterModule, RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "./auth.config";
import { AppService } from './app.service';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { CollapsableBottomComponent } from './collapsable-bottom/collapsable-bottom.component';
import { MainButtonsComponent } from './main-buttons/main-buttons.component';
import {ApiModule, FormsService} from './api-client';
import { HttpClientModule } from '@angular/common/http';
import { Configuration } from './api-client';
import { DefaultService } from './api-client';
import { LoginComponent } from './login/login.component';


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
    this.updateToken();
    if(!this.oauthService.hasValidAccessToken()) {
      this.router.navigateByUrl('/login');
    }


    });

  }

  private updateToken() {
    this.defaultservice.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.formsService.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
  }

  constructor(private oauthService: OAuthService, private appService: AppService, private defaultservice: DefaultService, private formsService: FormsService, private router :Router) {
  this.configure();
  this.service=appService;


  }

  testDL(){
    this.formsService.formsFormIDDownloadGet("BLA").subscribe((response) => {
      const downloadLink = document.createElement('a');
      downloadLink.href = URL.createObjectURL(response);

      const fileName = "test"
      downloadLink.download = fileName;
      downloadLink.click();
    })
  }

  login() {
    this.oauthService.initCodeFlow();
  }



  logout() {
    this.oauthService.logOut();

  }
}
