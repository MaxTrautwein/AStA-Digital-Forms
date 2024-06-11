import { Component } from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {MarkedItemComponent} from "./marked-item/marked-item.component";
import {TokenService} from "../token.service";
import {Favourite, FavouritesService, Form, FormsService} from "../api-client";
import {TemplateService} from "../template.service";
import {Observable, Subject, Subscriber} from "rxjs";
import {SearchService} from "../search.service";

@Component({
  selector: 'app-filled-forms',
  standalone: true,
  imports: [
    AsyncPipe,
    MarkedItemComponent
  ],
  templateUrl: './filled-forms.component.html',
  styleUrl: './filled-forms.component.css'
})
export class FilledFormsComponent {


  constructor(private tokenService: TokenService, private api: FormsService, protected search: SearchService) {
    this.forms = this.formsEmitter.asObservable();
  }

  protected forms: Observable<Form[]>;
  private formsEmitter: Subject<Form[]> = new Subject<Form[]>();


  protected getFormShortName(form: Form){
    let ret = "";
    form.form?.forEach(fs => {
      const shortName = fs.items?.find(fe => fe.id == "bez")?.value
      if(shortName){
        ret = shortName;
      }
    })
    return ret;
  }

  private Init(){
    this.api.formsGet().subscribe( forms => {
      this.formsEmitter.next(forms);
    })

  }

  ngOnInit(): void {
    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.Init();
        }
      })
    }else {
      this.Init();
    }

  }

}
