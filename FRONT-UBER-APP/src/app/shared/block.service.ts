import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BlockSharedService {

  private username = new BehaviorSubject<string>('');

  username_subscriber$ = this.username.asObservable();

  private update_table = new BehaviorSubject<void>(undefined);

  update_table_subscriber$ = this.update_table.asObservable();

  constructor() { }

  sentUsername(usrn: string): void {
    this.username.next(usrn);
  }

  updateTable(): void {
    this.update_table.next();
  }
}
