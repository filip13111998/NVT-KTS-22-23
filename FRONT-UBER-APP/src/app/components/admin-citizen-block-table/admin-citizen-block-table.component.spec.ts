import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCitizenBlockTableComponent } from './admin-citizen-block-table.component';

describe('AdminCitizenBlockTableComponent', () => {
  let component: AdminCitizenBlockTableComponent;
  let fixture: ComponentFixture<AdminCitizenBlockTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCitizenBlockTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCitizenBlockTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
