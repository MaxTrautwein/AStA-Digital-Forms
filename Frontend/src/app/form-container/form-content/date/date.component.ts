import {AfterViewInit, Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
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
    if (this.value === undefined) {
      this.value = "";
    }
    this.input.nativeElement.value = this.value;
  }

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.value = inputElement.value;
    this.isValidDate = this.validateDate(this.value);
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
}
