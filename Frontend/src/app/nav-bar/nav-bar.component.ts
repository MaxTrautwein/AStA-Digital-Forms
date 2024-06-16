import {Component,OnInit,OnDestroy} from '@angular/core';
import { RouterLink } from "@angular/router";
import {SearchService} from "../search.service";
import {OAuthService} from "angular-oauth2-oidc";
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit, OnDestroy {
  protected isOpen!: boolean;
  protected isLoggedIn!: boolean;
  private authSubscription!: Subscription;

  constructor(protected search: SearchService, private oauthService: OAuthService) {}

  ngOnInit(): void {
    this.updateLoginStatus();
    this.authSubscription = this.oauthService.events.subscribe(event => {
      if (event.type === 'token_received' || event.type === 'token_expires' || event.type === 'logout') {
        this.updateLoginStatus();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  private updateLoginStatus(): void {
    this.isLoggedIn = this.oauthService.hasValidAccessToken();
  }

  onClick() {
    this.isOpen = !this.isOpen;
  }

  logout() {
    this.oauthService.logOut();
  }
}
