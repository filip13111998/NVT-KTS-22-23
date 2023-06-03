import { TestBed } from '@angular/core/testing';

import { DriverNotificationService } from './driver-notification.service';

describe('DriverNotificationService', () => {
  let service: DriverNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriverNotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
