import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import {FormSection} from "../../../api-client";

@Component({
    selector: 'app-bool',
    templateUrl: './bool.component.html',
    styleUrls: ['./bool.component.css'],
    standalone:true
})
export class BoolComponent {
    @Input() description: string | undefined = "";
    @Input() help: string | undefined = "";
    @Input() value: string | undefined = "";

    @Output() valueChanged = new EventEmitter<string>();

    @ViewChild('input') input: any;

    ngAfterViewInit() {
        if (this.value === undefined) {
            this.value = "falsch";
        }
        this.input.nativeElement.checked = this.value="wahr";
    }

    onValueChange(event: Event) {
        const inputElement = event.target as HTMLInputElement;
        if(inputElement.checked){
          this.value = "wahr";
        }
        this.valueChanged.emit(this.value);
    }
}
