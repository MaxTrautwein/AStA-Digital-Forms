import { Component, Input } from '@angular/core';
import { Form } from "../../api-client";
import { RouterLink } from "@angular/router";

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


  displayNameOrTile(): string{
    let ret = this.displayName();

    if (ret === ""){
      ret = "(-) " + this.form.titel!;
    }

    return ret;
  }

  displayName(): string{
    let ret = "";

    this.form.form?.forEach(fs => {
      const shortName = fs.items?.find(fe => fe.id == "bez")?.value
      if(shortName){
        ret = shortName;
      }
    })
    return ret;
  }


}
