import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenHistoryDetailViewComponent } from './citizen-history-detail-view.component';

describe('CitizenHistoryDetailViewComponent', () => {
  let component: CitizenHistoryDetailViewComponent;
  let fixture: ComponentFixture<CitizenHistoryDetailViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenHistoryDetailViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenHistoryDetailViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
