import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarkedItemComponent } from './marked-item.component';

describe('MarkedItemComponent', () => {
  let component: MarkedItemComponent;
  let fixture: ComponentFixture<MarkedItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarkedItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MarkedItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
