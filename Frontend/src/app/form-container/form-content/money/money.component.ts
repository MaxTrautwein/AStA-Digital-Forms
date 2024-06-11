import {Component, EventEmitter, Input, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
  selector: 'app-money',
  standalone: true,
  imports: [],
  templateUrl: './money.component.html',
  styleUrl: './money.component.css'
})
export class MoneyComponent {
@Input() description: string | undefined = "";
@Input() help: string | undefined = "";
@Input() value: string | undefined = "";

@Output() valueChanged = new EventEmitter<string>();

@ViewChild('input') input: any;
isValidValue: boolean = true;

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
    if (this.value !== "") {
      this.isValidValue = this.validateValue(this.value);
    } else {
      this.isValidValue = true;
    }
    this.valueChanged.emit(inputElement.value);
  }

  validateValue(value: string): boolean {
    const regex = /^-?\d+(?:[.,]\d+)?$/;
    return regex.test(value);
  }
  private updateInputValue() {
    if (this.value === undefined) {
      this.value = "";
    }
    if (this.input && this.input.nativeElement) {
      this.input.nativeElement.value = this.value;
    }
    if (this.value !== null && this.value !== "") {
      this.isValidValue = this.validateValue(this.value);
    }else {
      this.isValidValue = true;
    }
  }
}
