import { Component, EventEmitter, Input, Output, ViewChild, OnChanges} from '@angular/core';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-text',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './text.component.html',
  styleUrl: './text.component.css'
})
export class TextComponent implements OnChanges{
@Input() description: string | undefined;
@Input() help: string | undefined;
@Input() value: string | undefined;

@Output() valueChanged = new EventEmitter<string>();

@ViewChild('input') input: any;

  ngAfterViewInit(){
    if (this.value === undefined){
      this.value = ""
    }
    this.input.nativeElement.value = this.value;
  }

  onValueChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    this.valueChanged.emit(inputElement.value);
  }

  ngOnChanges(): void {
    if (this.value === undefined){
      this.value = ""
    }
    if(this.input != undefined) {
      this.input.nativeElement.value = this.value;
    }
  }

}
