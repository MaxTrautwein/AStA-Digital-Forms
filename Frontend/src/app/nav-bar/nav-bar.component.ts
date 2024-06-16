import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import {SearchService} from "../search.service";
import { OAuthService } from 'angular-oauth2-oidc';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit{
  protected isOpen: boolean = false;
  protected Username: string = "Account";

  onClick() {
    if(this.isOpen) {
      this.isOpen=false;
    } else {
      this.isOpen=true;
    }
  }

  ngOnInit(): void {
      this.test();
  }

  constructor(protected search: SearchService, protected oauthService: OAuthService) {

  }

  test() {
    let claims = this.oauthService.getIdentityClaims();
    if(!claims) return null;
    this.Username = claims['given_name'];
    console.log(claims['given_name']);
    return null;
  }


}
