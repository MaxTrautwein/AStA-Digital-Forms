import { Component, Input } from '@angular/core';
import { Favourite } from '../../api-client';
import { FavouriteService } from '../../favourite.service';
import { TemplateService } from '../../template.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-favoriten',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './favoriten.component.html',
  styleUrl: './favoriten.component.css'
})
export class FavoritenComponent {
  @Input() fav!: Favourite;

  protected isPopupOpen: boolean = false;

  openPopup() {
    this.isPopupOpen = true;
  }

  closePopup() {
    this.isPopupOpen = false;
  }

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


