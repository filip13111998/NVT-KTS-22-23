import { TestBed } from '@angular/core/testing';

import { CitizenNotificationsService } from './citizen-notifications.service';

describe('CitizenNotificationsService', () => {
  let service: CitizenNotificationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitizenNotificationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
