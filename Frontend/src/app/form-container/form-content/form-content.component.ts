import {Component, Input} from '@angular/core';
import {FormSection} from "../../api-client";
import {TextComponent} from "./text/text.component";
import {NgForOf, NgSwitch, NgSwitchCase, NgSwitchDefault} from "@angular/common";

@Component({
  selector: 'app-form-content',
  standalone: true,
  imports: [
    TextComponent,
    NgSwitchCase,
    NgForOf,
    NgSwitch,
    NgSwitchDefault
  ],
  templateUrl: './form-content.component.html',
  styleUrl: './form-content.component.css'
})
export class FormContentComponent {
  @Input() section!: FormSection | undefined;

}
