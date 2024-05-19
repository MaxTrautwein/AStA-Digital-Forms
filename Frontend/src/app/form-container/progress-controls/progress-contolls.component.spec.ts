import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressContollsComponent } from './progress-contolls.component';

describe('ProgressContollsComponent', () => {
  let component: ProgressContollsComponent;
  let fixture: ComponentFixture<ProgressContollsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgressContollsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProgressContollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
