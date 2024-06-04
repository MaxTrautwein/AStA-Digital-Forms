import { Injectable } from '@angular/core';
import { DefaultService, Form } from "./api-client";
import { OAuthService } from "angular-oauth2-oidc";
import {Observable, Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TemplateService {

  Forms!: Observable<Array<Form>>;
  private templates!: Form[];

  fetchTemplates() {
    this.oauthService.events.subscribe(e => {
      if (this.templates == undefined){
        if (e.type === "token_received" || (e.type === "discovery_document_loaded" && this.oauthService.hasValidAccessToken())){
          this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
          this.Forms = this.api.templatesGet();
          this.Forms.subscribe(forms => {
            this.templates = forms;
          })
        }
      }
    });
  }

  getTemplateDetails(id: string){
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    return  this.api.templatesTemplateIdGet(id)
  }

  getTemplate(id: string){
    return this.templates.find((f) => f.id === id)
  }

  constructor(private api: DefaultService, private oauthService: OAuthService) { }
}
