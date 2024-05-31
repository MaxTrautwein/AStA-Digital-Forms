import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormSection} from "../../api-client";
import {TextComponent} from "./text/text.component";
import { IbanComponent } from './iban/iban.component';
import { MoneyComponent } from './money/money.component';
import { AddressComponent } from './address/address.component';
import { DateComponent } from './date/date.component';
import { TextMultiLineComponent } from './text-multi-line/text-multi-line.component';

@Component({
  selector: 'app-form-content',
  standalone: true,
  imports: [
    TextComponent,IbanComponent,MoneyComponent,AddressComponent,DateComponent,TextMultiLineComponent,
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
