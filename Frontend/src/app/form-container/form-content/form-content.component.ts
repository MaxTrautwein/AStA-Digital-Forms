import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormElement, FormSection, UserData} from "../../api-client";
import {TextComponent} from "./text/text.component";
import { UserDataService } from '../../api-client';
import { FormsModule } from '@angular/forms';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-form-content',
  standalone: true,
  imports: [TextComponent, FormsModule],
  templateUrl: './form-content.component.html',
  styleUrl: './form-content.component.css'
})
export class FormContentComponent {
  @Input() section!: FormSection | undefined;

  @Output() formSectionEventEmitter = new EventEmitter<FormSection>();

  protected Kontaktdaten: string = "test";

  constructor(protected userdataservice: UserDataService, private oauthService: OAuthService) {}

  updateFormSection(value: { val: string; id: string | undefined }){
    // @ts-ignore // Can only be called if section not null
    this.section.items.find(i => i.id == value.id).value = value.val;
    this.formSectionEventEmitter.emit(this.section);
  }

  fillData() {
    let userdata: UserData = {};
    this.userdataservice.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.userdataservice.userDataGet().subscribe(Response => {
      this.Kontaktdaten = Response.adress!;
      //console.log(Response.CreditInstitute);
      //console.log(this.Kontaktdaten);

    });
  }

  putDataInFields(ud: UserData) {
    this.section!.items!.find(i => i.Description == "Kontaktdaten")!.value = ud.adress;
  }

}
