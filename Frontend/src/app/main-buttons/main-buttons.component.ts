import { Component, OnInit } from '@angular/core';
import { DefaultService } from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';
import { NgFor, NgIf } from '@angular/common';
import {RouterLink} from "@angular/router";
import {TemplateService} from "../template.service";
import { Form } from '../api-client';


@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [NgFor, RouterLink, NgIf],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{


  constructor(protected templateService: TemplateService) {
  };

  ngOnInit(): void {
    this.templateService.fetchTemplates()
  }
}
