import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCitizenBlockFormComponent } from './admin-citizen-block-form.component';

describe('AdminCitizenBlockFormComponent', () => {
  let component: AdminCitizenBlockFormComponent;
  let fixture: ComponentFixture<AdminCitizenBlockFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCitizenBlockFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCitizenBlockFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
