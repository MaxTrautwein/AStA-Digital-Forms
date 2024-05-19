import { Injectable } from '@angular/core';
import {DefaultService, Form} from "./api-client";
import {OAuthService} from "angular-oauth2-oidc";

@Injectable({
  providedIn: 'root'
})
export class TemplateService {

  templates: Form[] = [];

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

  getTemplate(id: string){
    return this.templates.find((f) => f.id === id)
  }

  constructor(private api: DefaultService, private oauthService: OAuthService) { }
}
