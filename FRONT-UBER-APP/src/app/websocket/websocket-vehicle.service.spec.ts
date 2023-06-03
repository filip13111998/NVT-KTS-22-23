import { TestBed } from '@angular/core/testing';

import { WebsocketVehicleService } from './websocket-vehicle.service';

describe('WebsocketVehicleService', () => {
  let service: WebsocketVehicleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WebsocketVehicleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
