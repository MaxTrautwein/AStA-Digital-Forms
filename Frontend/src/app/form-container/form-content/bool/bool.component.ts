import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';

@Component({
    selector: 'app-bool',
    templateUrl: './bool.component.html',
    styleUrls: ['./bool.component.css'],
    standalone:true
})
export class BoolComponent {
    @Input() description: string | undefined = "Bezahlt?";
    @Input() help: string | undefined = "";
    @Input() value: boolean | undefined = false;

    @Output() valueChanged = new EventEmitter<boolean>();

    @ViewChild('input') input: any;

    ngAfterViewInit() {
        if (this.value === undefined) {
            this.value = false;
        }
        this.input.nativeElement.checked = this.value;
    }

    onValueChange(event: Event) {
        const inputElement = event.target as HTMLInputElement;
        this.value = inputElement.checked;
        this.valueChanged.emit(this.value);
    }
}
