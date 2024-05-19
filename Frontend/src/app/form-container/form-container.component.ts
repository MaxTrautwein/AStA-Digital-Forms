import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {ActivatedRoute, ParamMap, RouterOutlet} from "@angular/router";
import {Form} from "../api-client";
import {TemplateService} from "../template.service";
import {Subscription} from "rxjs";
import {ProgressDisplayComponent} from "./progress-display/progress-display.component";
import {ProgressContollsComponent} from "./progress-controls/progress-contolls.component";

@Component({
  selector: 'app-form-container',
  standalone: true,
  imports: [
    NavBarComponent,
    RouterOutlet,
    ProgressDisplayComponent,
    ProgressContollsComponent
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
  }

  ngOnDestroy() {
    this.routeSub?.unsubscribe();
  }

}
