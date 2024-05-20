import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-progress-controls',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './progress-contolls.component.html',
  styleUrl: './progress-controls.component.css'
})
export class ProgressContollsComponent {
@Input() sectionsCnt: number | undefined = 0;
@Input() currentSection: number = 0;

}
