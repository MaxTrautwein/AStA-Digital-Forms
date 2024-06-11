import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import {SearchService} from "../search.service";
import {OAuthService} from "angular-oauth2-oidc";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  protected isOpen: boolean = false;

  onClick() {
    if(this.isOpen) {
      this.isOpen=false;
    } else {
      this.isOpen=true;
    }
  }

  constructor(protected search: SearchService, private OAuthService: OAuthService) {

  }
  logout() {
    this.OAuthService.logOut();
  }


}
