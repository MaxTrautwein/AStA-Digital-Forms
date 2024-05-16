import { Component, OnInit } from '@angular/core';
import { DefaultService, FormElement, FormSection } from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent{



  constructor(private oathservice: OAuthService, private defaultservice: DefaultService) {
    //defaultservice.defaultHeaders.append('Test', 'Bitte lass das im Browser stehen!');
    defaultservice.configuration.credentials
  };

  async getTemplates() {
    this.defaultservice.templatesGet().subscribe(Response => {
      alert('It worked');
    });
  }
}
