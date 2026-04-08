import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CourseOutcomeRequest, CourseOutcomeResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class CourseOutcomeService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/course-outcomes`;

  findAll(): Observable<CourseOutcomeResponse[]> {
    return this.http.get<CourseOutcomeResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<CourseOutcomeResponse> {
    return this.http.get<CourseOutcomeResponse>(`${this.baseUrl}/${id}`);
  }

  findByCourseId(courseId: number): Observable<CourseOutcomeResponse[]> {
    return this.http.get<CourseOutcomeResponse[]>(`${this.baseUrl}/course/${courseId}`);
  }

  create(req: CourseOutcomeRequest): Observable<CourseOutcomeResponse> {
    return this.http.post<CourseOutcomeResponse>(this.baseUrl, req);
  }

  update(id: number, req: CourseOutcomeRequest): Observable<CourseOutcomeResponse> {
    return this.http.put<CourseOutcomeResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
