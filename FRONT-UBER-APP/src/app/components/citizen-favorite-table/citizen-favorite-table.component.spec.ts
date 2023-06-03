import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenFavoriteTableComponent } from './citizen-favorite-table.component';

describe('CitizenFavoriteTableComponent', () => {
  let component: CitizenFavoriteTableComponent;
  let fixture: ComponentFixture<CitizenFavoriteTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenFavoriteTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenFavoriteTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
