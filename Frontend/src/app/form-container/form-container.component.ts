import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {ActivatedRoute, ParamMap, Router, RouterOutlet} from "@angular/router";
import {Favourite, Form, FormSection, FormsService} from "../api-client";
import {TemplateService} from "../template.service";
import {map, Observable, Subscription, switchMap} from "rxjs";
import {ProgressDisplayComponent} from "./progress-display/progress-display.component";
import {ProgressContollsComponent} from "./progress-controls/progress-contolls.component";
import {AsyncPipe, NgIf} from "@angular/common";
import {FormContentComponent} from "./form-content/form-content.component";
import {PrepareAPIService} from "../prepare-api.service";
import { FavouriteService } from '../favourite.service';


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

  protected form!: Form;

  protected section: FormSection | undefined;

  constructor(private route: ActivatedRoute, private templateService: TemplateService,
              private api: FormsService, private prep: PrepareAPIService,
              private router: Router, protected FavService: FavouriteService) {
  }

  getFormId() {
    return this.formdetails.subscribe(responce => responce.id);
  }

  isInFav() {
    let Favourites = this.FavService.getFav();
    for(let favs of Favourites) {
      if(favs.formId == this.form.id) {
        return true;
      }
    }
    return false;
  }

  getFavId() {
    for(let fav of this.FavService.getFav()) {
      if(this.form.id == fav.formId) {
        return fav.id;
      }
    }
    return null;                                //not clean, but it works!
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
    this.formdetails.subscribe(e => this.form = e);
  }

  private awaitSave: boolean = false

  UpdateFormSectionData(s: FormSection){
    var index = this.form.form!.findIndex(fs => fs.order == s.order)!
    if (index !== -1){
      this.form.form![index] = s;
    }

    console.log(this.form)
    //now we can auto-save it

    this.prep.prepare()
    if(this.form.template){
      //we need to await the first save
      if(!this.awaitSave){
        this.awaitSave = true;
        this.api.formsPost(this.form).subscribe(r =>{
          this.form = r;
          this.awaitSave = false
        })
      }
    }else {
      //We have a Document
      const tmp = this.form;


      this.api.formsFormIDPut(tmp.id!, tmp).subscribe(r =>{
        // Go to the new URL so that we can Reload the current State
        this.route.paramMap.pipe(
          switchMap((params: ParamMap) => params.get('id')!)).subscribe(i => {
          if (i != r.id){
            this.router.navigateByUrl("/Form/" + r.id)
          }
        })
      })
    }


  }
}
