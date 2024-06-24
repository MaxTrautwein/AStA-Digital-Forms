import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {Favourite, FavouritesService, Form, FormSection, FormsService, UserDataService} from "../api-client";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {TemplateService} from "../template.service";
import {map, Observable, Subscriber, switchMap} from "rxjs";
import {ProgressDisplayComponent} from "./progress-display/progress-display.component";
import {ProgressContollsComponent} from "./progress-controls/progress-contolls.component";
import {AsyncPipe} from "@angular/common";
import {FormContentComponent} from "./form-content/form-content.component";
import {TokenService} from "../token.service";
import {OverviewComponent} from "./overview/overview.component";


@Component({
  selector: 'app-form-container',
  standalone: true,
  imports: [
    ProgressDisplayComponent,
    ProgressContollsComponent,
    AsyncPipe,
    FormContentComponent,
    OverviewComponent
  ],
  templateUrl: './form-container.component.html',
  styleUrl: './form-container.component.css'
})
export class FormContainerComponent {

  protected currentSection : number = 0;

  protected formdetails!: Observable<Form>;

  protected form!: Form;

  protected section: FormSection | undefined;

  private isFavEmitter: Subscriber<Favourite> = new Subscriber<Favourite>();
  protected isFav: Observable<Favourite>;

  constructor(private route: ActivatedRoute, private templateService: TemplateService,
              private api: FormsService, private tokenService: TokenService,
              private router: Router, protected FavService: FavouritesService,
              private userDataService: UserDataService) {
    this.isFav = new Observable<Favourite>(e => this.isFavEmitter = e)

  }
  protected addFav(form: Form){
    let myFav: Favourite = {};
    myFav.formId = form.parent;
    if (myFav.formId == undefined){
      myFav.formId = form.id;
    }


    this.FavService.favouritesPost(myFav).subscribe(e => {
      this.requestFavStatus(e.formId!);
    })
  }
  protected rmFav(FavId: string, formId: string) {
    this.FavService.favouritesIdDelete(FavId).subscribe(e => {
        this.requestFavStatus(formId);
    })
  }

  private requestFavStatus(id: string){
    this.FavService.favouritesIdGet(id).subscribe(
      {
        next: ((f: Favourite) => this.isFavEmitter.next(f)),
        error: ((e:any) => {
          const fav: Favourite = {};
          this.isFavEmitter.next(fav)}
        )
      }

    )
  }


  private Initialize(){
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

      if(this.form.template){
        // We need to Create the User form
        this.api.formsPost(this.form).subscribe(r =>{
          // After the Creation Move to that new Form URL
          this.router.navigateByUrl("/Form/" + r.id)
        })
      }else {
        // Check for Fav
        this.requestFavStatus(e.parent!);
      }
    });
  }

  ngOnInit() {

    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.Initialize();
        }
      })
    }else {
      this.Initialize();
    }
  }

  UpdateFormSectionData(s: FormSection){
    const index = this.form.form!.findIndex(fs => fs.order == s.order)!
    if (index !== -1){
      this.form.form![index] = s;
    }

    this.SendUpdate()
  }

  private SendUpdate(){
    this.api.formsFormIDPut(this.form.id!, this.form).subscribe()
  }

  fillData() {
    this.userDataService.userDataGet().subscribe(Response => {
      for(let sec of this.form.form!) {
        for(let formElements of sec.items!) {
          switch(formElements.type) {
            case("address"): {
              if (["prepay_adress"].includes(formElements.id!) ){
                formElements.value = Response.adress;
              }
              break;
            }
            case("iban"): {
              formElements.value = Response.IBAN;
              break;
            }
            case("text"): {
              if (["user", "prepay_empf"].includes(formElements.id!)) {
                formElements.value = Response.firstName + " " + Response.name;
              } else if (formElements.id == "prepay_bank") {
                formElements.value = Response.CreditInstitute;
              } else if(formElements.id == "contact") {
                formElements.value = Response.email;
              }
              break;
            }

          }
        }
      }
      this.formdetails = this.api.formsFormIDPut(this.form.id!, this.form).pipe(formdata => formdata);
    });
  }

}
