import {AfterViewInit, Component, EventEmitter, Input, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormSection} from "../../../api-client";
@Component({
  selector: 'app-date',
  standalone: true,
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.css']
})
export class DateComponent implements AfterViewInit {
  @Input() description: string | undefined = "";
  @Input() help: string | undefined = "";
  @Input() value: string | undefined = "";

  @Output() valueChanged = new EventEmitter<string>();

  @ViewChild('input') input: any;
  isValidDate: boolean = true;

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
      this.isValidDate = this.validateDate(this.value);
    } else {
      this.isValidDate = true;
    }
    this.valueChanged.emit(this.value);
  }

  validateDate(dateString: string): boolean {
    const date = new Date(dateString);
    const minDate = new Date('1900-01-01');
    const maxDate = new Date('2100-01-01');
    if (isNaN(date.getTime())) {
      return false;
    }
    return date >= minDate && date <= maxDate;
  }
  private updateInputValue() {
    if (this.value === undefined) {
      this.value = "";
    }
    if (this.input && this.input.nativeElement) {
      this.input.nativeElement.value = this.value;
    }
    if (this.value !== null && this.value !== "") {
      this.isValidDate = this.validateDate(this.value);
    } else {
      this.isValidDate = true;
    }
  }
}
