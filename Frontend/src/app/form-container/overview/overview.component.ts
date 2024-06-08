import {Component, Input} from '@angular/core';
import {Attachment, Form, FormsService} from "../../api-client";
import {AttachmentComponent} from "./attachment/attachment.component";

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [
    AttachmentComponent
  ],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent {
  @Input() form!: Form;

  constructor(private api: FormsService) {
  }

  DownloadPDF(id: string){
    this.api.formsFormIDDownloadGet(id).subscribe((response) => {
      const downloadLink = document.createElement('a');
      downloadLink.href = URL.createObjectURL(response);
      const fileName = "test"
      downloadLink.download = fileName;
      downloadLink.click();
    })
  }

  protected readonly Attachment = Attachment;
}
