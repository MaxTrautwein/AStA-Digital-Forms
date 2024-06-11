import {Component, EventEmitter, Input, Output, ViewChild, AfterViewInit, SimpleChanges} from '@angular/core';
//import { ValidatorService} from 'angular-iban';
import {FormSection} from "../../../api-client";
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
    this.updateInputValue();
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['value']) {
      this.updateInputValue();
    }
  }

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.value = inputElement.value;
   // this.isValidIban = this.validatorService.validateIban(this.value); // IBAN validieren
    this.valueChanged.emit(this.value);
  }
  private updateInputValue() {
    if (this.value === undefined) {
      this.value = "";
    }
    if (this.input && this.input.nativeElement) {
      this.input.nativeElement.value = this.value;
    }
  }
}
