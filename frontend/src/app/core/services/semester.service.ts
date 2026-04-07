import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SemesterRequest, SemesterResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class SemesterService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/semesters`;

  findAll(): Observable<SemesterResponse[]> {
    return this.http.get<SemesterResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<SemesterResponse> {
    return this.http.get<SemesterResponse>(`${this.baseUrl}/${id}`);
  }

  findByAcademicYearId(academicYearId: number): Observable<SemesterResponse[]> {
    return this.http.get<SemesterResponse[]>(`${this.baseUrl}/academic-year/${academicYearId}`);
  }

  create(req: SemesterRequest): Observable<SemesterResponse> {
    return this.http.post<SemesterResponse>(this.baseUrl, req);
  }

  update(id: number, req: SemesterRequest): Observable<SemesterResponse> {
    return this.http.put<SemesterResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
