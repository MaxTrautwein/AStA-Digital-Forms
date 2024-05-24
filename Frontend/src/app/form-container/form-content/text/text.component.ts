import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
  selector: 'app-text',
  standalone: true,
  imports: [],
  templateUrl: './text.component.html',
  styleUrl: './text.component.css'
})
export class TextComponent {
@Input() description: string | undefined = "";
@Input() help: string | undefined = "";

@Output() valueChanged = new EventEmitter<string>();

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.valueChanged.emit(inputElement.value);
  }
}
