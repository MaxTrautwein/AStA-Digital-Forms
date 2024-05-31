import {Component, Input} from '@angular/core';
import {Form} from "../../api-client";
import {RouterLink} from "@angular/router";
import { FavouriteService } from '../../favourite.service';

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
  @Input() form!: Form;

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

  FavId() {
    return this.form.id!;
  }

  constructor(protected FavService: FavouriteService) {};

}
