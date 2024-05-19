import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {ActivatedRoute, ParamMap, RouterOutlet} from "@angular/router";
import {Form} from "../api-client";
import {TemplateService} from "../template.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-form-container',
  standalone: true,
  imports: [
    NavBarComponent,
    RouterOutlet
  ],
  templateUrl: './form-container.component.html',
  styleUrl: './form-container.component.css'
})
export class FormContainerComponent {

  protected templateForm : Form | undefined;

  private routeSub: Subscription | undefined;
  constructor(private route: ActivatedRoute, private templateService: TemplateService) {
  }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.templateForm = this.templateService.getTemplate(params['id'])
    });
    //this.Form$ = this.templateService.getTemplate(this.route.params.get('id'));
  }

  ngOnDestroy() {
    this.routeSub?.unsubscribe();
  }

}
