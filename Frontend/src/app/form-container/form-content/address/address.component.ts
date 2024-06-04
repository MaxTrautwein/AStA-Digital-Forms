import { Component, EventEmitter, Input, Output, ViewChild, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-address',
  standalone: true,
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements AfterViewInit {
  @Input() description: string | undefined = "Straße:";
  @Input() description2: string | undefined = "Stadt";
  @Input() description3: string | undefined = "Postleitzahl:";
  @Input() help: string | undefined = "";
  @Input() value: { street: string, city: string, postalCode: string } | undefined = { street: "", city: "", postalCode: "" };

  @Output() valueChanged = new EventEmitter<{ street: string, city: string, postalCode: string }>();

  @ViewChild('streetInput') streetInput: any;
  @ViewChild('cityInput') cityInput: any;
  @ViewChild('postalCodeInput') postalCodeInput: any;

  ngAfterViewInit() {
    if (this.value === undefined) {
      this.value = { street: "", city: "", postalCode: "" };
    }
    this.streetInput.nativeElement.value = this.value.street;
    this.cityInput.nativeElement.value = this.value.city;
    this.postalCodeInput.nativeElement.value = this.value.postalCode;
  }

  onValueChange() {
    this.value = {
      street: this.streetInput.nativeElement.value,
      city: this.cityInput.nativeElement.value,
      postalCode: this.postalCodeInput.nativeElement.value
    };
    this.valueChanged.emit(this.value);
  }
}
