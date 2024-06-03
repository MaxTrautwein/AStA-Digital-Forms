import { Component } from '@angular/core';
import {CollapsableBottomComponent} from "../collapsable-bottom/collapsable-bottom.component";
import {MainButtonsComponent} from "../main-buttons/main-buttons.component";


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CollapsableBottomComponent,
    MainButtonsComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
