import {Component, EventEmitter, Input, Output} from '@angular/core';
import { FormSection } from "../../api-client";
import {TextComponent} from "./text/text.component";
import { IbanComponent } from './iban/iban.component';
import { MoneyComponent } from './money/money.component';
import { AddressComponent } from './address/address.component';
import { DateComponent } from './date/date.component';
import { TextMultiLineComponent } from './text-multi-line/text-multi-line.component';
import { BoolComponent } from './bool/bool.component';
import { UserDataService } from '../../api-client';
import { FormsModule } from '@angular/forms';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-form-content',
  standalone: true,
  imports: [
    TextComponent,IbanComponent,MoneyComponent,AddressComponent,DateComponent,BoolComponent,TextMultiLineComponent,FormsModule,
  ],
  templateUrl: './form-content.component.html',
  styleUrl: './form-content.component.css'
})
export class FormContentComponent {
  @Input() section!: FormSection | undefined;

  @Input() sections!: Array<FormSection> | undefined;

  @Output() formSectionEventEmitter = new EventEmitter<FormSection>();

  constructor(protected userdataservice: UserDataService, private oauthService: OAuthService) {}

  updateFormSection(value: { val: string; id: string | undefined }){
    // @ts-ignore // Can only be called if section not null
    this.section.items.find(i => i.id == value.id).value = value.val;
    this.formSectionEventEmitter.emit(this.section);
  }
}
