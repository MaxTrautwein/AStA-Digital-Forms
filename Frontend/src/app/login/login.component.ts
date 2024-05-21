import { ChangeDetectionStrategy, Component, Input } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { AppComponent } from "../app.component";
@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  standalone: true,
  imports: [FormsModule, CommonModule,AppComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {}
