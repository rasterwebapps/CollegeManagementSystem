import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LabTypeRequest, LabTypeResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class LabTypeService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/lab-types`;

  findAll(): Observable<LabTypeResponse[]> {
    return this.http.get<LabTypeResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<LabTypeResponse> {
    return this.http.get<LabTypeResponse>(`${this.baseUrl}/${id}`);
  }

  create(req: LabTypeRequest): Observable<LabTypeResponse> {
    return this.http.post<LabTypeResponse>(this.baseUrl, req);
  }

  update(id: number, req: LabTypeRequest): Observable<LabTypeResponse> {
    return this.http.put<LabTypeResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
