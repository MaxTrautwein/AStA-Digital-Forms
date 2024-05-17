import { Component, OnInit } from '@angular/core';
import { DefaultService, Form} from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [NgFor],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{
  
  
  constructor(private defaultservice: DefaultService, private oauthService: OAuthService) {
  };
  templates: any = [];

  async getTemplates() {
    this.defaultservice.templatesGet().subscribe((Response) => {
      alert('It worked');
      this.templates = Response;
    });
  }

  ngOnInit(): void {
    this.defaultservice.configuration.accessToken = this.oauthService.getAccessToken();
    this.getTemplates();
  }
}
