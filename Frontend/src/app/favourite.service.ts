import { Injectable } from '@angular/core';
import {DefaultService, Favourite} from "./api-client";
import {OAuthService} from "angular-oauth2-oidc";
import { FavouritesService } from './api-client';

@Injectable({
  providedIn: 'root'
})
export class FavouriteService {
  favoriten: Favourite[] = [];
  id: string = "";

  fetchFav() {
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.api.favouritesGet().subscribe((Response) => {
        this.favoriten = Response;
      });
  }

  getFav() {
    return this.favoriten;
  }

  putFav(Formid: string) {
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    let fav: Favourite = {};
    fav.formId = Formid;
    this.api.favouritesPost(fav).subscribe();
  }

  delFav(FavId: string) {
    this.api.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.api.favouritesFavIdDelete(FavId).subscribe();
  }



  constructor(private api: FavouritesService, private oauthService: OAuthService) { }
}
