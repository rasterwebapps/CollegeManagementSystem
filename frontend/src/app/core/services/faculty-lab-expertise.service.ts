import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  FacultyLabExpertiseRequest,
  FacultyLabExpertiseResponse,
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class FacultyLabExpertiseService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/faculty-lab-expertise`;

  findByFacultyId(facultyId: number): Observable<FacultyLabExpertiseResponse[]> {
    return this.http.get<FacultyLabExpertiseResponse[]>(
      `${this.baseUrl}/faculty/${facultyId}`,
    );
  }

  findById(id: number): Observable<FacultyLabExpertiseResponse> {
    return this.http.get<FacultyLabExpertiseResponse>(`${this.baseUrl}/${id}`);
  }

  create(req: FacultyLabExpertiseRequest): Observable<FacultyLabExpertiseResponse> {
    return this.http.post<FacultyLabExpertiseResponse>(this.baseUrl, req);
  }

  update(
    id: number,
    req: FacultyLabExpertiseRequest,
  ): Observable<FacultyLabExpertiseResponse> {
    return this.http.put<FacultyLabExpertiseResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
