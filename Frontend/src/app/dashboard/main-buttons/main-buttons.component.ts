import { Component, OnInit } from '@angular/core';
import {AsyncPipe, NgFor, NgIf} from '@angular/common';
import {RouterLink} from "@angular/router";
import {TemplateService} from "../../template.service";
import {GroupsService, TemplateGroup} from "../../api-client";
import {OAuthService} from "angular-oauth2-oidc";
import {TokenService} from "../../token.service";
import {Observable, Subscriber} from "rxjs";
import {SearchService} from "../../search.service";


@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [NgFor, RouterLink, NgIf, AsyncPipe],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{


  constructor(protected search: SearchService, protected groupApi: GroupsService,
              protected tokenService: TokenService) {
    this.groups = new Observable(e => this.groupEmitter = e);

  };

  private TokenReady = false
  protected groups: Observable<TemplateGroup[]>;
  private groupEmitter: Subscriber<TemplateGroup[]> = new Subscriber<TemplateGroup[]>();

  ngOnInit(): void {
    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.groupApi.groupsGet().subscribe(r => this.groupEmitter.next(r))
        }
      })
    }else {
      this.groupApi.groupsGet().subscribe(r => this.groupEmitter.next(r))
    }

  }
}
