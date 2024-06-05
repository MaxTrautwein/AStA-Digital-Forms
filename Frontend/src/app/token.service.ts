import { Injectable } from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {Observable, Subscriber} from "rxjs";
import {DefaultService, FavouritesService, FormsService, GroupsService} from "./api-client";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private emitter: Subscriber<Boolean> = new Subscriber<Boolean>();

  // Notify That a Token is ready
  public tokenReady:  Observable<Boolean>;

  public hasValidToken(): Boolean{
    let tokenStatus: Boolean = this.oauthService.hasValidAccessToken();

    if (tokenStatus){
      this.setBearerAuth();
    }

    return tokenStatus
  }

  private setBearerAuth(){
    this.defaultService.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.formService.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.groupService.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.favoritesService.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
  }

  constructor(private oauthService: OAuthService, private defaultService: DefaultService, private formService: FormsService,
              private groupService: GroupsService, private favoritesService: FavouritesService) {
    this.tokenReady = new Observable(e => this.emitter = e);
    //this.emitter.next(false);

    this.oauthService.events.subscribe(e => {

      if (e.type === "token_received" || (e.type === "discovery_document_loaded" && this.oauthService.hasValidAccessToken())){

        this.setBearerAuth();

        if(!this.emitter.closed){
          this.emitter.next(true);
          this.emitter.complete();
        }

      }


    })


  }



}
