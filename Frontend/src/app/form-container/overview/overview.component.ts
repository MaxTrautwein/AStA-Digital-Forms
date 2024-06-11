import {Component, Input} from '@angular/core';
import {Attachment, Favourite, Form, FormsService} from "../../api-client";
import {AttachmentComponent} from "./attachment/attachment.component";
import {TokenService} from "../../token.service";
import {Observable, Subject, Subscriber} from "rxjs";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [
    AttachmentComponent,
    AsyncPipe
  ],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent {
  @Input() formId!: string;

  private attachmentsEmitter: Subject<Attachment[]> = new Subject<Attachment[]>();
  protected attachments: Observable<Attachment[]>;

  constructor(private api: FormsService, private tokenService: TokenService) {
    this.attachments = this.attachmentsEmitter.asObservable();
  }

  protected filterRequired(attach: Attachment[]): Attachment[]{
    return attach.filter( a => a.required === Attachment.RequiredEnum.Conditional || a.required === Attachment.RequiredEnum.Always)
  }
  protected filterUser(attach: Attachment[]): Attachment[]{
    return attach.filter( a => a.required === Attachment.RequiredEnum.User)
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

  private Initialize(){
    this.api.formsFormIDAttchmentsGet(this.formId).subscribe( a => {
      this.attachmentsEmitter.next(a);
    })

  }

  ngOnInit() {

    if (!this.tokenService.hasValidToken()){
      this.tokenService.tokenReady.subscribe(s => {
        if (s){
          this.Initialize();
        }
      })
    }else {
      this.Initialize();
    }
  }

  protected readonly Attachment = Attachment;
}
