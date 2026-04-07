import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CourseRequest, CourseResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/courses`;

  findAll(): Observable<CourseResponse[]> {
    return this.http.get<CourseResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<CourseResponse> {
    return this.http.get<CourseResponse>(`${this.baseUrl}/${id}`);
  }

  findByProgramId(programId: number): Observable<CourseResponse[]> {
    return this.http.get<CourseResponse[]>(`${this.baseUrl}/program/${programId}`);
  }

  findByDepartmentId(departmentId: number): Observable<CourseResponse[]> {
    return this.http.get<CourseResponse[]>(`${this.baseUrl}/department/${departmentId}`);
  }

  create(req: CourseRequest): Observable<CourseResponse> {
    return this.http.post<CourseResponse>(this.baseUrl, req);
  }

  update(id: number, req: CourseRequest): Observable<CourseResponse> {
    return this.http.put<CourseResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
