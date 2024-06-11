import {AfterViewInit, Component, EventEmitter, Input, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
    selector: 'app-bool',
    templateUrl: './bool.component.html',
    styleUrls: ['./bool.component.css'],
    standalone:true
})
export class BoolComponent implements AfterViewInit {
    @Input() description: string | undefined = "";
    @Input() help: string | undefined = "";
    @Input() value: string | undefined = "";

    @Output() valueChanged = new EventEmitter<string>();

    @ViewChild('input') input: any;

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
      this.value = inputElement.checked ? "true" : "false";
      this.valueChanged.emit(this.value);
    }
  private updateInputValue() {
    if (this.value === undefined || this.value == null) {
      this.value = "";
    }
    if (this.input && this.input.nativeElement) {
      this.input.nativeElement.checked = (this.value === "true");
    }
  }
}
