import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DepartmentRequest, DepartmentResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/departments`;

  findAll(): Observable<DepartmentResponse[]> {
    return this.http.get<DepartmentResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<DepartmentResponse> {
    return this.http.get<DepartmentResponse>(`${this.baseUrl}/${id}`);
  }

  create(req: DepartmentRequest): Observable<DepartmentResponse> {
    return this.http.post<DepartmentResponse>(this.baseUrl, req);
  }

  update(id: number, req: DepartmentRequest): Observable<DepartmentResponse> {
    return this.http.put<DepartmentResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
