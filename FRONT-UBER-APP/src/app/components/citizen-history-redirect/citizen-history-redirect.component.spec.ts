import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenHistoryRedirectComponent } from './citizen-history-redirect.component';

describe('CitizenHistoryRedirectComponent', () => {
  let component: CitizenHistoryRedirectComponent;
  let fixture: ComponentFixture<CitizenHistoryRedirectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenHistoryRedirectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenHistoryRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
