import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCitizenBlockComponent } from './admin-citizen-block.component';

describe('AdminCitizenBlockComponent', () => {
  let component: AdminCitizenBlockComponent;
  let fixture: ComponentFixture<AdminCitizenBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCitizenBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCitizenBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
