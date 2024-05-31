import {Component, Input} from '@angular/core';
import {Form} from "../../api-client";
import {RouterLink} from "@angular/router";
import { FavouriteService } from '../../favourite.service';
import { TemplateService } from '../../template.service';
import { Favourite } from '../../api-client';

@Component({
  selector: 'app-marked-item',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './marked-item.component.html',
  styleUrl: './marked-item.component.css'
})
export class MarkedItemComponent {
  @Input() fav!: Favourite;

  protected isPopupOpen: boolean = false;

  openPopup() {
    this.isPopupOpen = true;
  }

  closePopup() {
    this.isPopupOpen = false;
  }

  /*
  displayName(): string{
    let ret = this.form.titel!;

    this.form.form?.forEach(fs => {
      const shortName = fs.items?.find(fe => fe.id == "bez")?.value
      if(shortName){
        ret = shortName;
      }
    })
    return ret;
  }
*/
  FavId() {
    return this.fav.id!;
  }

  FormId() {
    return this.fav.formId;
  }

  getTemplateName() {
    return this.templateService.getTemplate(this.FormId()!)?.titel;
  }

  constructor(protected FavService: FavouriteService, protected templateService: TemplateService) {};

}
