import {Component, Input} from '@angular/core';

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
}
