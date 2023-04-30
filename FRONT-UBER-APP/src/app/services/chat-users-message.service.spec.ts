import { TestBed } from '@angular/core/testing';

import { ChatUsersMessageService } from './chat-users-message.service';

describe('ChatUsersMessageService', () => {
  let service: ChatUsersMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatUsersMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
