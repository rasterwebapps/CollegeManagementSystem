import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LabRequest, LabResponse, LabSummaryResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class LabService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/labs`;

  findAll(): Observable<LabResponse[]> {
    return this.http.get<LabResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<LabResponse> {
    return this.http.get<LabResponse>(`${this.baseUrl}/${id}`);
  }

  findByDepartmentId(departmentId: number): Observable<LabResponse[]> {
    return this.http.get<LabResponse[]>(`${this.baseUrl}/department/${departmentId}`);
  }

  getSummary(): Observable<LabSummaryResponse> {
    return this.http.get<LabSummaryResponse>(`${this.baseUrl}/summary`);
  }

  create(req: LabRequest): Observable<LabResponse> {
    return this.http.post<LabResponse>(this.baseUrl, req);
  }

  update(id: number, req: LabRequest): Observable<LabResponse> {
    return this.http.put<LabResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
