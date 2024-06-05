import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { TemplateService } from '../template.service';
import { FavouriteService } from '../favourite.service';
import { FavoritenComponent } from './favoriten/favoriten.component';
import { MarkedItemComponent } from "./marked-item/marked-item.component";
import {Favourite, FavouritesService, Form, FormsService, TemplateGroup} from "../api-client";
import {Observable, Subscriber} from "rxjs";
import { AsyncPipe } from "@angular/common";
import {TokenService} from "../token.service";
@Component({
  selector: 'app-collapsable-bottom',
  standalone: true,
  imports: [ReactiveFormsModule, MarkedItemComponent, AsyncPipe, FavoritenComponent],
  templateUrl: './collapsable-bottom.component.html',
  styleUrl: './collapsable-bottom.component.css'
})
export class CollapsableBottomComponent {

  protected forms: Observable<Form[]>

  constructor(private tokenService: TokenService, private api: FormsService,
              protected templateService: TemplateService, protected favService: FavouritesService) {
    this.forms = api.formsGet()
    this.favourites = new Observable(e => this.favEmitter = e);
  }

  protected favourites: Observable<Favourite[]>;
  private favEmitter: Subscriber<Favourite[]> = new Subscriber<Favourite[]>();


  protected LoadFav(){
    console.log("loaded Fav");
    this.favService.favouritesGet().subscribe(r => this.favEmitter.next(r))
  }

  ngOnInit(): void {
    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.LoadFav();
        }
      })
    }else {
      this.LoadFav();
    }

  }


}
