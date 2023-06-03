import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenFavoriteComponent } from './citizen-favorite.component';

describe('CitizenFavoriteComponent', () => {
  let component: CitizenFavoriteComponent;
  let fixture: ComponentFixture<CitizenFavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenFavoriteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenFavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
