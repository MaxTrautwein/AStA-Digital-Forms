import { Component} from '@angular/core';
import { UserDataService } from '../api-client';
import { FormsModule } from '@angular/forms';
import { OnInit } from '@angular/core';
import { UserData } from '../api-client';
import { OAuthService } from 'angular-oauth2-oidc';


@Component({
  selector: 'app-user-data',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './user-data.component.html',
  styleUrl: './user-data.component.css'
})





export class UserDataComponent implements OnInit{
  
  public name: string | undefined;
  public vorname: string | undefined;
  public adresse: string | undefined;
  public email: string | undefined;
  public iban: string | undefined;
  public kreditinstitut: string | undefined;


  

  constructor(private userservice: UserDataService, private oauthService: OAuthService) {}

  sync() {
    let userdata: UserData = {}
    userdata.name = this.name;
    userdata.firstName = this.vorname;
    userdata.adress = this.adresse;
    userdata.email = this.email;
    userdata.IBAN = this.iban;
    userdata.CreditInstitute = this.kreditinstitut;
    return userdata;
  }

  ngOnInit(): void {
    this.userservice.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
      this.userservice.userDataGet().subscribe(Response => {
      if(Response) {
      this.name = Response.name;
      this.vorname = Response.firstName;
      this.adresse = Response.adress;
      this.email = Response.email;
      this.iban = Response.IBAN;
      this.kreditinstitut = Response.CreditInstitute;
      }
    })
  }

  save() {
    this.userservice.userDataPost(this.sync()).subscribe();
  }

  
}
