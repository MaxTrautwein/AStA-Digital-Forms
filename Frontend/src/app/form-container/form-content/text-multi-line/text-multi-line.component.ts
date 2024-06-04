import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-text-multi-line',
  standalone: true,
  imports: [],
  templateUrl: './text-multi-line.component.html',
  styleUrl: './text-multi-line.component.css'
})
export class TextMultiLineComponent  {
  @Input() description: string | undefined = "MultiTextFeld";
  @Input() help: string | undefined = "";
  @Input() value: string | undefined = "";
  
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
  }
  
