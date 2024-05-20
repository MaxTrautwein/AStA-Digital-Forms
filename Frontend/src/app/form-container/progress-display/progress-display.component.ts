import {Component, Input} from '@angular/core';
import {FormSection} from "../../api-client";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-progress-display',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './progress-display.component.html',
  styleUrl: './progress-display.component.css'
})
export class ProgressDisplayComponent {
  @Input() sections!: Array<FormSection> | undefined;
  protected readonly Input = Input;
}
