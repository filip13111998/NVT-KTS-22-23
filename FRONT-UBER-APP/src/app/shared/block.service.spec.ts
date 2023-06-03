import { TestBed } from '@angular/core/testing';

import { BlockSharedService } from './block.service';

describe('BlockService', () => {
  let service: BlockSharedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BlockSharedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
