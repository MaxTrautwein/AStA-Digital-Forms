import { Injectable } from '@angular/core';
import { FormsService } from "./api-client";
import { OAuthService } from "angular-oauth2-oidc";

@Injectable({
  providedIn: 'root'
})
export class PrepareAPIService {

  constructor(private api: FormsService, private oauthService: OAuthService) {

  }

  prepare(){
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
  }
}
