import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapsableBottomComponent } from './collapsable-bottom.component';

describe('CollapsableBottomComponent', () => {
  let component: CollapsableBottomComponent;
  let fixture: ComponentFixture<CollapsableBottomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollapsableBottomComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CollapsableBottomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
