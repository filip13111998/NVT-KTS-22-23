import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenMenuComponent } from './citizen-menu.component';

describe('CitizenMenuComponent', () => {
  let component: CitizenMenuComponent;
  let fixture: ComponentFixture<CitizenMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
