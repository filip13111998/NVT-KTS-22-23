import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriverBlockFormComponent } from './admin-driver-block-form.component';

describe('AdminDriverBlockFormComponent', () => {
  let component: AdminDriverBlockFormComponent;
  let fixture: ComponentFixture<AdminDriverBlockFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDriverBlockFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDriverBlockFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
