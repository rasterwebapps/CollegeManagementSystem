import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  LabStaffAssignmentRequest,
  LabStaffAssignmentResponse,
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class LabStaffService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/lab-staff`;

  findByLabId(labId: number): Observable<LabStaffAssignmentResponse[]> {
    return this.http.get<LabStaffAssignmentResponse[]>(`${this.baseUrl}/lab/${labId}`);
  }

  findById(id: number): Observable<LabStaffAssignmentResponse> {
    return this.http.get<LabStaffAssignmentResponse>(`${this.baseUrl}/${id}`);
  }

  create(req: LabStaffAssignmentRequest): Observable<LabStaffAssignmentResponse> {
    return this.http.post<LabStaffAssignmentResponse>(this.baseUrl, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
