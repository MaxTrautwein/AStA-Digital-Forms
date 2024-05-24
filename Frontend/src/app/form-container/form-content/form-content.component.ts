import {Component, EventEmitter, Input, Output} from '@angular/core';
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

  @Output() formSectionEventEmitter = new EventEmitter<FormSection>();

  updateFormSection(value: { val: string; id: string | undefined }){
    // @ts-ignore // Can only be called if section not null
    this.section.items.find(i => i.id == value.id).value = value.val;
    this.formSectionEventEmitter.emit(this.section);
  }

}
