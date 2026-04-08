import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ExperimentRequest, ExperimentResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ExperimentService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/experiments`;

  findAll(): Observable<ExperimentResponse[]> {
    return this.http.get<ExperimentResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<ExperimentResponse> {
    return this.http.get<ExperimentResponse>(`${this.baseUrl}/${id}`);
  }

  findByCourseId(courseId: number): Observable<ExperimentResponse[]> {
    return this.http.get<ExperimentResponse[]>(`${this.baseUrl}/course/${courseId}`);
  }

  create(req: ExperimentRequest): Observable<ExperimentResponse> {
    return this.http.post<ExperimentResponse>(this.baseUrl, req);
  }

  update(id: number, req: ExperimentRequest): Observable<ExperimentResponse> {
    return this.http.put<ExperimentResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
