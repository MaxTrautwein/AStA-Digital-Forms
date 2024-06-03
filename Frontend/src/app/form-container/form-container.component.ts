import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {Favourite, Form, FormSection, FormsService} from "../api-client";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {TemplateService} from "../template.service";
import {map, Observable, switchMap} from "rxjs";
import {ProgressDisplayComponent} from "./progress-display/progress-display.component";
import {ProgressContollsComponent} from "./progress-controls/progress-contolls.component";
import {AsyncPipe} from "@angular/common";
import {FormContentComponent} from "./form-content/form-content.component";
import {PrepareAPIService} from "../prepare-api.service";
import { FavouriteService } from '../favourite.service';


@Component({
  selector: 'app-form-container',
  standalone: true,
  imports: [
    ProgressDisplayComponent,
    ProgressContollsComponent,
    AsyncPipe,
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
    // Get the current Page Section
    this.route.queryParamMap.pipe(map((params: ParamMap) => params.get('page'))).subscribe(
        page => {
          if (page !== null){
            this.currentSection = Number(page)
          }
        })

    // Request The Form
    this.formdetails = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.templateService.getTemplateDetails(params.get('id')!)));

    // Await Form Load
    this.formdetails.subscribe(e => {
      this.form = e;

      this.prep.prepare()
      if(this.form.template){
        // We need to Create the User form
        this.api.formsPost(this.form).subscribe(r =>{
            // After the Creation Move to that new Form URL
            this.router.navigateByUrl("/Form/" + r.id)
        })
      }
    });
  }

  UpdateFormSectionData(s: FormSection){
    const index = this.form.form!.findIndex(fs => fs.order == s.order)!
    if (index !== -1){
      this.form.form![index] = s;
    }

    this.SendUpdate()
  }

  private SendUpdate(){
    this.prep.prepare()
    this.api.formsFormIDPut(this.form.id!, this.form).subscribe()
  }

  testDL(id: string){
    this.api.formsFormIDDownloadGet(id).subscribe((response) => {
      const downloadLink = document.createElement('a');
      downloadLink.href = URL.createObjectURL(response);

      const fileName = "test"
      downloadLink.download = fileName;
      downloadLink.click();
    })
  }


}
