import { Component } from '@angular/core';
import { DefaultService} from '../api-client';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent{
  
  
  constructor(private defaultservice: DefaultService) {};
  
  async getTemplates() {
    this.defaultservice.templatesGet().subscribe(Response => {
      alert('It worked');
    });
  }
}
