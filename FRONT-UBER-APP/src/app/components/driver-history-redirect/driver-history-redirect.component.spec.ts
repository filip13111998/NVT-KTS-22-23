import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverHistoryRedirectComponent } from './driver-history-redirect.component';

describe('DriverHistoryRedirectComponent', () => {
  let component: DriverHistoryRedirectComponent;
  let fixture: ComponentFixture<DriverHistoryRedirectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DriverHistoryRedirectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DriverHistoryRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
