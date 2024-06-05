import { Component} from '@angular/core';
import { UserDataService } from '../api-client';
import { FormsModule } from '@angular/forms';
import { DefaultService } from '../api-client';
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

  private ud: UserData = {};
  

  constructor(private userservice: UserDataService, private oauthService: OAuthService) {}

  sync() {
    this.ud.name = this.name;
    this.ud.firstName = this.vorname;
    this.ud.adress = this.adresse;
    this.ud.email = this.email;
    this.ud.IBAN = this.iban;
    this.ud.CreditInstitute = this.kreditinstitut;
  }

  ngOnInit(): void {
    this.userservice.configuration.credentials["BearerAuth"] = this.oauthService.getAccessToken();
    this.userservice.userDataGet().subscribe(Response => {
      this.ud = Response;
    })
      
    
  }

  save() {
    this.sync();
    this.userservice.userDataPost(this.ud);

  }

  
}
