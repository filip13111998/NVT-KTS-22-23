import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenHistoryComponent } from './citizen-history.component';

describe('CitizenHistoryComponent', () => {
  let component: CitizenHistoryComponent;
  let fixture: ComponentFixture<CitizenHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
