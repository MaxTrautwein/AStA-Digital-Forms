import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
  selector: 'app-money',
  standalone: true,
  imports: [],
  templateUrl: './money.component.html',
  styleUrl: './money.component.css'
})
export class MoneyComponent {
@Input() description: string | undefined = "Betrag";
@Input() help: string | undefined = "";
@Input() value: string | undefined = "";

@Output() valueChanged = new EventEmitter<string>();

@ViewChild('input') input: any;

  ngAfterViewInit(){
    if (this.value === undefined){
      this.value = ""
    }
    this.input.nativeElement.value = this.value;
  }

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.valueChanged.emit(inputElement.value);
  }
}
