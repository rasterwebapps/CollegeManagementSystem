import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { FacultyProfileRequest, FacultyProfileResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class FacultyService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/faculty`;

  findAll(): Observable<FacultyProfileResponse[]> {
    return this.http.get<FacultyProfileResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<FacultyProfileResponse> {
    return this.http.get<FacultyProfileResponse>(`${this.baseUrl}/${id}`);
  }

  findByDepartmentId(departmentId: number): Observable<FacultyProfileResponse[]> {
    return this.http.get<FacultyProfileResponse[]>(
      `${this.baseUrl}/department/${departmentId}`,
    );
  }

  findByKeycloakUserId(keycloakUserId: string): Observable<FacultyProfileResponse> {
    return this.http.get<FacultyProfileResponse>(
      `${this.baseUrl}/keycloak/${keycloakUserId}`,
    );
  }

  create(req: FacultyProfileRequest): Observable<FacultyProfileResponse> {
    return this.http.post<FacultyProfileResponse>(this.baseUrl, req);
  }

  update(id: number, req: FacultyProfileRequest): Observable<FacultyProfileResponse> {
    return this.http.put<FacultyProfileResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
