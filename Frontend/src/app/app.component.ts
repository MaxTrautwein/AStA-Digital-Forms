import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { CollapsableBottomComponent } from './collapsable-bottom/collapsable-bottom.component';
import { MainButtonsComponent } from './main-buttons/main-buttons.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavBarComponent, CollapsableBottomComponent, MainButtonsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'DigitalForms';
}
