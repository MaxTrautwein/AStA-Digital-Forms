import { ChangeDetectionStrategy, Component, Input } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { AppComponent } from "../app.component";
import { CollapsableBottomComponent } from "../collapsable-bottom/collapsable-bottom.component";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.css"],
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [FormsModule, CommonModule, AppComponent, CollapsableBottomComponent]
})
export class LoginComponent {
  loggedIn: boolean;
  constructor(private appComponent: AppComponent) { this.loggedIn= this.appComponent.checkLoginStatus();}

  login() {
    this.appComponent.login();
  }
 

}
