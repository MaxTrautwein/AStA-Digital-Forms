import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextMultiLineComponent } from './text-multi-line.component';

describe('TextMultiLineComponent', () => {
  let component: TextMultiLineComponent;
  let fixture: ComponentFixture<TextMultiLineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextMultiLineComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TextMultiLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
