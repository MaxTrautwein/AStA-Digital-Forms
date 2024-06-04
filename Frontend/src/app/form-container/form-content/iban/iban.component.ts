import { Component, EventEmitter, Input, Output, ViewChild, AfterViewInit } from '@angular/core';
//import { ValidatorService} from 'angular-iban';

@Component({
  selector: 'app-iban',
  standalone: true,
  templateUrl: './iban.component.html',
  styleUrls: ['./iban.component.css'],
  providers: []
})
export class IbanComponent implements AfterViewInit {
  @Input() description: string | undefined = "";
  @Input() help: string | undefined = "";
  @Input() value: string | undefined = "";
  @Output() valueChanged = new EventEmitter<string>();

  @ViewChild('input') input: any;
  isValidIban: boolean = true; // Flag für die IBAN-Validierung

  //constructor(private validatorService: ValidatorService) {}

  ngAfterViewInit() {
    if (this.value === undefined) {
      this.value = "";
    }
    this.input.nativeElement.value = this.value;
  }

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.value = inputElement.value;
   // this.isValidIban = this.validatorService.validateIban(this.value); // IBAN validieren
    this.valueChanged.emit(this.value);
  }
}
