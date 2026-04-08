import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  FacultyLabAssignmentRequest,
  FacultyLabAssignmentResponse,
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class FacultyLabAssignmentService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/faculty-lab-assignments`;

  findByFacultyId(facultyId: number): Observable<FacultyLabAssignmentResponse[]> {
    return this.http.get<FacultyLabAssignmentResponse[]>(
      `${this.baseUrl}/faculty/${facultyId}`,
    );
  }

  findByLabId(labId: number): Observable<FacultyLabAssignmentResponse[]> {
    return this.http.get<FacultyLabAssignmentResponse[]>(`${this.baseUrl}/lab/${labId}`);
  }

  findById(id: number): Observable<FacultyLabAssignmentResponse> {
    return this.http.get<FacultyLabAssignmentResponse>(`${this.baseUrl}/${id}`);
  }

  create(req: FacultyLabAssignmentRequest): Observable<FacultyLabAssignmentResponse> {
    return this.http.post<FacultyLabAssignmentResponse>(this.baseUrl, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
