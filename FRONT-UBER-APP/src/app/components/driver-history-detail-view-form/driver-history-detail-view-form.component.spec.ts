import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverHistoryDetailViewFormComponent } from './driver-history-detail-view-form.component';

describe('DriverHistoryDetailViewFormComponent', () => {
  let component: DriverHistoryDetailViewFormComponent;
  let fixture: ComponentFixture<DriverHistoryDetailViewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DriverHistoryDetailViewFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DriverHistoryDetailViewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
