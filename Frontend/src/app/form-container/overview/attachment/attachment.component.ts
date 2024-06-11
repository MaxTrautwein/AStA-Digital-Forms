import {Component, Input} from '@angular/core';
import {Attachment} from "../../../api-client";
@Component({
  selector: 'app-attachment',
  standalone: true,
  imports: [],
  templateUrl: './attachment.component.html',
  styleUrl: './attachment.component.css'
})
export class AttachmentComponent {
  @Input() attachment!: Attachment;

}
