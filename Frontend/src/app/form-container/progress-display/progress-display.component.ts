import {Component, Input} from '@angular/core';
import {FormSection} from "../../api-client";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-progress-display',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './progress-display.component.html',
  styleUrl: './progress-display.component.css'
})
export class ProgressDisplayComponent {
  @Input() sections!: Array<FormSection> | undefined;
  protected readonly Input = Input;
}
