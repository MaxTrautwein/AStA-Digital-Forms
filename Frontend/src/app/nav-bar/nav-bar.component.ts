import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import {SearchService} from "../search.service";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  protected isOpen: boolean = false;

  onClick() {
    if(this.isOpen) {
      this.isOpen=false;
    } else {
      this.isOpen=true;
    }
    console.log(this.isOpen);
  }

  constructor(protected search: SearchService) {

  }



}
