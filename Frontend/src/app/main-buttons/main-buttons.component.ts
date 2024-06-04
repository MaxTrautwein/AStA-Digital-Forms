import { Component, OnInit } from '@angular/core';
import {AsyncPipe, NgFor, NgIf} from '@angular/common';
import {RouterLink} from "@angular/router";
import {TemplateService} from "../template.service";
import {Form} from "../api-client";


@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [NgFor, RouterLink, NgIf, AsyncPipe],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{


  constructor(protected templateService: TemplateService) {
  };

  ngOnInit(): void {
    this.templateService.fetchTemplates()
  }

  protected readonly Form = Form;
}
