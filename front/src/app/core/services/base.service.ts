import { HttpHeaders } from '@angular/common/http';
import { Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BaseService {
  protected getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
  }
}
