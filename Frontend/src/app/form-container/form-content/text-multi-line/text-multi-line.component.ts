import {AfterViewInit, Component, EventEmitter, Input, Output, SimpleChanges, ViewChild} from '@angular/core';

@Component({
  selector: 'app-text-multi-line',
  standalone: true,
  imports: [],
  templateUrl: './text-multi-line.component.html',
  styleUrl: './text-multi-line.component.css'
})
export class TextMultiLineComponent implements AfterViewInit {
  @Input() description: string | undefined = "MultiTextFeld";
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

