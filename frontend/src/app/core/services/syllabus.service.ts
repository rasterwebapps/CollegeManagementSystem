import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SyllabusRequest, SyllabusResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class SyllabusService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/syllabi`;

  findByCourseId(courseId: number): Observable<SyllabusResponse[]> {
    return this.http.get<SyllabusResponse[]>(`${this.baseUrl}/course/${courseId}`);
  }

  findById(id: number): Observable<SyllabusResponse> {
    return this.http.get<SyllabusResponse>(`${this.baseUrl}/${id}`);
  }

  createOrUpdate(req: SyllabusRequest): Observable<SyllabusResponse> {
    return this.http.post<SyllabusResponse>(this.baseUrl, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
