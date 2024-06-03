import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoritenComponent } from './favoriten.component';

describe('FavoritenComponent', () => {
  let component: FavoritenComponent;
  let fixture: ComponentFixture<FavoritenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavoritenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavoritenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
