import { Component, OnInit } from '@angular/core';
import { DefaultService, FormElement, FormSection } from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';
import { AppComponent } from '../app.component';
import { Token } from '@angular/compiler';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent{
  
  
  constructor(private defaultservice: DefaultService, private oauthservice: OAuthService) {

  };
  
  async getTemplates() {
    //this.defaultservice.configuration.accessToken = this.oauthservice.getAccessToken();
    alert(this.defaultservice.configuration.accessToken);
    this.defaultservice.templatesGet().subscribe(Response => {
      alert('It worked');
    });
    
   
  }
}
