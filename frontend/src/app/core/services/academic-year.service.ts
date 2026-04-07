import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AcademicYearRequest, AcademicYearResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class AcademicYearService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/academic-years`;

  findAll(): Observable<AcademicYearResponse[]> {
    return this.http.get<AcademicYearResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<AcademicYearResponse> {
    return this.http.get<AcademicYearResponse>(`${this.baseUrl}/${id}`);
  }

  findCurrent(): Observable<AcademicYearResponse> {
    return this.http.get<AcademicYearResponse>(`${this.baseUrl}/current`);
  }

  create(req: AcademicYearRequest): Observable<AcademicYearResponse> {
    return this.http.post<AcademicYearResponse>(this.baseUrl, req);
  }

  update(id: number, req: AcademicYearRequest): Observable<AcademicYearResponse> {
    return this.http.put<AcademicYearResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
