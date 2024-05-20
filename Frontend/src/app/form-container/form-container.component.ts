import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {ActivatedRoute, ParamMap, RouterOutlet} from "@angular/router";
import {Form, FormSection} from "../api-client";
import {TemplateService} from "../template.service";
import {map, Observable, Subscription, switchMap} from "rxjs";
import {ProgressDisplayComponent} from "./progress-display/progress-display.component";
import {ProgressContollsComponent} from "./progress-controls/progress-contolls.component";
import {AsyncPipe, NgIf} from "@angular/common";
import {FormContentComponent} from "./form-content/form-content.component";

@Component({
  selector: 'app-form-container',
  standalone: true,
  imports: [
    NavBarComponent,
    RouterOutlet,
    ProgressDisplayComponent,
    ProgressContollsComponent,
    AsyncPipe,
    NgIf,
    FormContentComponent
  ],
  templateUrl: './form-container.component.html',
  styleUrl: './form-container.component.css'
})
export class FormContainerComponent {

  protected currentSection : number = 0;

  protected formdetails!: Observable<Form>;

  protected section: FormSection | undefined;

  constructor(private route: ActivatedRoute, private templateService: TemplateService) {
  }

  ngOnInit() {
    this.route.queryParamMap.pipe(
      map((params: ParamMap) => params.get('page'))).subscribe(
        page => {
          if (page !== null){
            this.currentSection = Number(page)
          }
        }
    )

    this.formdetails = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.templateService.getTemplateDetails(params.get('id')!)));
  }

  getCurrentSection(){
    return  this.formdetails?.subscribe(form => {
      console.log(form.form ? undefined : form.form?.[this.currentSection])
      return form.form ? undefined : form.form?.[this.currentSection];
    })

  }
}
