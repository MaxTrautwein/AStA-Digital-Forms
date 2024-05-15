import { Component, OnInit } from '@angular/core';
import { FormElement, FormSection } from '../api-client';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-buttons',
  standalone: true,
  imports: [],
  templateUrl: './main-buttons.component.html',
  styleUrl: './main-buttons.component.css'
})
export class MainButtonsComponent implements OnInit{
  Forms: FormElement[] | undefined;

  constructor(private router: Router, private formSection: FormSection) {}

  ngOnInit(): void {
    this.Forms = this.formSection.items;
  } 
}
