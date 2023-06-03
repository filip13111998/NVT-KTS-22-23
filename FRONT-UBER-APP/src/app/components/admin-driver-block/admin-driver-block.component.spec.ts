import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriverBlockComponent } from './admin-driver-block.component';

describe('AdminDriverBlockComponent', () => {
  let component: AdminDriverBlockComponent;
  let fixture: ComponentFixture<AdminDriverBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDriverBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDriverBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
