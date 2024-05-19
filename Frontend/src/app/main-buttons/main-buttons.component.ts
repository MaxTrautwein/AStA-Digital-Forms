import { Component, OnInit } from '@angular/core';
import { DefaultService } from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';
import { NgFor } from '@angular/common';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{


  constructor(private defaultservice: DefaultService, private oauthService: OAuthService) {
  };
  templates: any = [];

  async getTemplates() {
    this.defaultservice.templatesGet().subscribe((Response) => {
      this.templates = Response;
    });
  }

  ngOnInit(): void {
    this.defaultservice.configuration.accessToken = this.oauthService.getAccessToken();
    this.getTemplates();
  }
}
