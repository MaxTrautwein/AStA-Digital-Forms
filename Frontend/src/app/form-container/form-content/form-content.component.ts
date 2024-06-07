import {Component, EventEmitter, Input, Output} from '@angular/core';
import { FormSection } from "../../api-client";
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

  @Input() sections!: Array<FormSection> | undefined;

  @Output() formSectionEventEmitter = new EventEmitter<FormSection>();

  constructor(protected userdataservice: UserDataService, private oauthService: OAuthService) {}

  updateFormSection(value: { val: string; id: string | undefined }){
    // @ts-ignore // Can only be called if section not null
    this.section.items.find(i => i.id == value.id).value = value.val;
    this.formSectionEventEmitter.emit(this.section);
  }

  fillData() {
    for(let sec of this.sections!) {
      this.userdataservice.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
      this.userdataservice.userDataGet().subscribe(Response => {
        for(let formElements of sec.items!) {
          switch(formElements.id) {
            case("contact"): {
              formElements.value = Response.adress;
              break;
            }
            case("user"): {
              formElements.value = Response.firstName + " " + Response.name;
              break;
            }
            case("prepay_bank"): {
              formElements.value = Response.CreditInstitute;
              break;
            }
            default: {
              formElements.value = "";
            }
          }
        }
      });
    }
  }
}
