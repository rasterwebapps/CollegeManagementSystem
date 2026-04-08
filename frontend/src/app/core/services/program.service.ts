import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProgramRequest, ProgramResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ProgramService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/programs`;

  findAll(): Observable<ProgramResponse[]> {
    return this.http.get<ProgramResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<ProgramResponse> {
    return this.http.get<ProgramResponse>(`${this.baseUrl}/${id}`);
  }

  findByDepartmentId(departmentId: number): Observable<ProgramResponse[]> {
    return this.http.get<ProgramResponse[]>(`${this.baseUrl}/department/${departmentId}`);
  }

  create(req: ProgramRequest): Observable<ProgramResponse> {
    return this.http.post<ProgramResponse>(this.baseUrl, req);
  }

  update(id: number, req: ProgramRequest): Observable<ProgramResponse> {
    return this.http.put<ProgramResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
