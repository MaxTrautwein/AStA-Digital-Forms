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
    this.api.favouritesGet().subscribe((Response) => {
        this.favoriten = Response;
      });
  }

  getFav() {
    return this.favoriten;
  }

  putFav(Formid: string, id: string) {
      let fav: Favourite = {};
    if(Formid == null) {
      fav.formId = id;
    } else {
      fav.formId = Formid;
    }
    this.api.favouritesPost(fav).subscribe();
  }

  delFav(FavId: string) {
    this.api.favouritesFavIdDelete(FavId).subscribe();
  }



  constructor(private api: FavouritesService, private oauthService: OAuthService) { }
}
