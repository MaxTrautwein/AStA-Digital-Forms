import {Component, EventEmitter, Input, Output, ViewChild, AfterViewInit, SimpleChanges} from '@angular/core';
import {FormSection} from "../../../api-client";
@Component({
  selector: 'app-address',
  standalone: true,
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements AfterViewInit {
  @Input() description: string | undefined = "";
  @Input() help: string | undefined = "";
  @Input() value: { street: string, city: string, postalCode: string } | string | undefined = { street: "", city: "", postalCode: "" };

  @Output() valueChanged = new EventEmitter<string>();

  @ViewChild('streetInput') streetInput: any;
  @ViewChild('cityInput') cityInput: any;
  @ViewChild('postalCodeInput') postalCodeInput: any;

  isValidPostalCode: boolean = true;

  ngAfterViewInit() {
    this.updateInputValues();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['value']) {
      this.updateInputValues();
    }
  }

  onValueChange() {
    const street = this.streetInput.nativeElement.value;
    const city = this.cityInput.nativeElement.value;
    const postalCode = this.postalCodeInput.nativeElement.value;

    this.value = { street, city, postalCode };
    if (postalCode === "") {
      this.isValidPostalCode = true;
    } else {
      this.isValidPostalCode = this.validatePostalCode(postalCode);
    }
    const concatenatedValue = `${street}, ${city}, ${postalCode}`;
    this.valueChanged.emit(concatenatedValue);
  }

  validatePostalCode(value: string): boolean {
    const regex = /^[0-9]+$/;
    return regex.test(value);
  }

  splitConcatenatedString(value: string): { street: string, city: string, postalCode: string } {
    const [street, city, postalCode] = value.split(',').map(part => part.trim());
    return { street, city, postalCode };
  }

private updateInputValues() {

  if (typeof this.value === 'string') {
    this.value = this.splitConcatenatedString(this.value);
  } else if (this.value === undefined || this.value === null) {
    this.value = { street: "", city: "", postalCode: "" };
  }

  if (this.streetInput && this.streetInput.nativeElement) {
    this.streetInput.nativeElement.value = this.value.street;
  }
  if (this.cityInput && this.cityInput.nativeElement) {
    this.cityInput.nativeElement.value = this.value.city;
  }
  if (this.postalCodeInput && this.postalCodeInput.nativeElement) {
    this.postalCodeInput.nativeElement.value = this.value.postalCode;
  }
  if (this.value.postalCode !== "") {
    this.isValidPostalCode = this.validatePostalCode(this.value.postalCode);
  } else {
    this.isValidPostalCode = true;
  }
}
}
