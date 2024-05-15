import { Component } from '@angular/core';
import { FormElement, FormSection } from '../api-client';
import { FormArray } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent {
  Forms: FormElement[] | undefined;

  constructor(private router: Router, private formSection: FormSection) {}

  async onPageLoad(): Promise<any> {
    this.Forms = this.formSection.items;
  }
}
