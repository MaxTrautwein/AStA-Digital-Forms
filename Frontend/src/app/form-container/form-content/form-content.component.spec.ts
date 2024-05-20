import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormContentComponent } from './form-content.component';

describe('FormContentComponent', () => {
  let component: FormContentComponent;
  let fixture: ComponentFixture<FormContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormContentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
