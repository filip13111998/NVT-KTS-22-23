import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenFavoriteFormComponent } from './citizen-favorite-form.component';

describe('CitizenFavoriteFormComponent', () => {
  let component: CitizenFavoriteFormComponent;
  let fixture: ComponentFixture<CitizenFavoriteFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenFavoriteFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenFavoriteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
