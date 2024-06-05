import { Component} from '@angular/core';
import { UserDataService } from '../api-client';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-user-data',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './user-data.component.html',
  styleUrl: './user-data.component.css'
})





export class UserDataComponent {
  public inputWert: string | undefined;

  constructor(private userservice: UserDataService) {}

  onSubmit() {
    console.log(this.inputWert);

    
  }
}
