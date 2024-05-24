import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import {OAuthService} from "angular-oauth2-oidc";
import { Router } from "@angular/router";
@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.css"],
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [FormsModule],

})


export class LoginComponent implements OnInit {

  constructor(private OAuthService: OAuthService, private router: Router) {
    }

  ngOnInit(): void {
    if (this.OAuthService.hasValidAccessToken()) {
      this.router.navigate(['/dashboard']);
    }
  }

  login() {
    this.OAuthService.initCodeFlow();
  }
 

}
