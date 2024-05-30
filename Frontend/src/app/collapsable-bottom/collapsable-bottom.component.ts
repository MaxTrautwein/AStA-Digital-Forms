import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import {MarkedItemComponent} from "./marked-item/marked-item.component";
import {Form, FormsService} from "../api-client";
import {Observable} from "rxjs";
import {AsyncPipe, NgForOf} from "@angular/common";
import {PrepareAPIService} from "../prepare-api.service";
import { TemplateService } from '../template.service';
import { FavouriteService } from '../favourite.service';

@Component({
  selector: 'app-collapsable-bottom',
  standalone: true,
  imports: [ReactiveFormsModule, MarkedItemComponent, AsyncPipe, NgForOf],
  templateUrl: './collapsable-bottom.component.html',
  styleUrl: './collapsable-bottom.component.css'
})
export class CollapsableBottomComponent {

  protected forms: Observable<Form[]>

  constructor(private api: FormsService, private prep: PrepareAPIService, protected templateService: TemplateService, protected favService: FavouriteService) {
    prep.prepare();
    this.forms = api.formsGet()
    favService.fetchFav();
  }
}
