import { Injectable } from '@angular/core';
import {DefaultService, Form} from "./api-client";
import {OAuthService} from "angular-oauth2-oidc";

@Injectable({
  providedIn: 'root'
})
export class TemplateService {

  templates: Form[] = [];
  abrechnungen: Form[] = [];
  anträge: Form[] = [];


  fetchTemplates() {
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.api.templatesGet().subscribe((Response) => {
      this.templates = Response;
      console.log(Response)
    });
  }

  getTemplates(){
    return this.templates;
  }

  getAbrechnungsTemplates() {
    for (var element of this.templates) {
      console.log(element);
      console.log(Form.CategoryEnum.Abrechnung);
      if (element.category == Form.CategoryEnum.Abrechnung) {
        this.abrechnungen.push(element);
      }
    }
    return this.abrechnungen;
  }

  getAntragsTemplates() {
    for (var element of this.templates) {
      if (element.category == Form.CategoryEnum.Antrag) {
        this.anträge.push(element);
      }
    }
    return this.anträge;
  }




  getTemplate(id: string){
    return this.templates.find((f) => f.id === id)
  }

  getTemplateDetails(id: string){
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    return  this.api.templatesTemplateIdGet(id)
  }

  constructor(private api: DefaultService, private oauthService: OAuthService) { }
}
