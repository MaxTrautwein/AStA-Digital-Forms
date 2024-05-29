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

  isAntrag(f: Form): boolean {
    return f.category == Form.CategoryEnum.Antrag;
  }

  isAbrechnung(f: Form): boolean {
    return f.category == Form.CategoryEnum.Abrechnung;
  }

  getAbrechnungsTemplates() {
    this.abrechnungen = [];
    for (var element of this.templates) {
      if (element.category == Form.CategoryEnum.Abrechnung) {
        this.abrechnungen.push(element);
      }
    }
    return this.abrechnungen;
  }

  getAntragsTemplates() {
    this.anträge = [];
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
