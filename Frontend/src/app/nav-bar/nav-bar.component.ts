import { Component, Input } from '@angular/core';
import { RouterLink } from "@angular/router";
import {SearchService} from "../search.service";
import { OAuthService } from 'angular-oauth2-oidc';
import { OnInit, OnChanges } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import {of, Observable, Subscriber, map} from "rxjs";
import { TokenService } from '../token.service';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink, AsyncPipe
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit{
  protected isLoggedIn: Observable<boolean> = of(false);
  protected isOpen: boolean = false;
  protected Username: string = "Account";
 

  onClick() {
    if(this.isOpen) {
      this.isOpen=false;
    } else {
      this.isOpen=true;
    }
  }

  init() {
    console.log("was hier los?");
    this.updateUsername();
    this.isUserLoggedIn();
  }

  ngOnInit(): void {
    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.init()
        }
      })
    }else {
      this.init();
    }
  }

  constructor(protected search: SearchService, protected oauthService: OAuthService, private tokenService: TokenService) {
    
  }

  updateUsername() {
    let claims = this.oauthService.getIdentityClaims();
    if(!this.isLoggedIn) { 
      this.Username = "default";
    } else {
      if(claims != null) {
        this.Username = claims['given_name'];
        console.log(claims['given_name']);
      }
    }
  }

  isUserLoggedIn() {
    this.isLoggedIn = of(this.oauthService.hasValidAccessToken());
  }




}
