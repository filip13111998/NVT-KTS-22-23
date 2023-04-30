import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriverChatComponent } from './admin-driver-chat.component';

describe('AdminDriverChatComponent', () => {
  let component: AdminDriverChatComponent;
  let fixture: ComponentFixture<AdminDriverChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDriverChatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDriverChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
