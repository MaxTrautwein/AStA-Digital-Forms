import {AfterViewInit, Component, EventEmitter, Input, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
  selector: 'app-text',
  standalone: true,
  imports: [],
  templateUrl: './text.component.html',
  styleUrl: './text.component.css'
})
export class TextComponent implements AfterViewInit {
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
    this.valueChanged.emit(inputElement.value);
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