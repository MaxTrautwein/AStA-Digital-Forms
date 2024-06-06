import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DefaultService, Favourite, FavouritesService, Form} from '../../api-client';
import {TemplateService} from '../../template.service';
import {RouterLink} from '@angular/router';
import {TokenService} from "../../token.service";
import {map, Observable} from "rxjs";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-favoriten',
  standalone: true,
  imports: [RouterLink, AsyncPipe],
  templateUrl: './favoriten.component.html',
  styleUrl: './favoriten.component.css'
})
export class FavoritenComponent {
  @Input() fav!: Favourite;
  @Output() deletedFav: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  protected name: Observable<String | undefined> | undefined;
  protected Form: Observable<Form> | undefined;

  protected isPopupOpen: boolean = false;

  openPopup() {
    this.isPopupOpen = true;
  }

  closePopup() {
    this.isPopupOpen = false;
  }

  protected deleteFavId(id: string){
    this.favService.favouritesIdDelete(id).subscribe(e => {
      this.deletedFav.next(true);
    })
    this.closePopup()
  }

  constructor(protected tokenService: TokenService,  protected favService: FavouritesService,
              protected templateService: TemplateService, protected defaultApi: DefaultService) {
  };

  ngOnInit(): void {
    this.name = this.defaultApi.templatesTemplateIdGet(this.fav.formId!).pipe(
      map(form => form.titel )
    )
  }

}


