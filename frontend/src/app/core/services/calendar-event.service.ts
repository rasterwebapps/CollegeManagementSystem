import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CalendarEventRequest, CalendarEventResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class CalendarEventService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/calendar-events`;

  findAll(): Observable<CalendarEventResponse[]> {
    return this.http.get<CalendarEventResponse[]>(this.baseUrl);
  }

  findById(id: number): Observable<CalendarEventResponse> {
    return this.http.get<CalendarEventResponse>(`${this.baseUrl}/${id}`);
  }

  findByAcademicYearId(academicYearId: number): Observable<CalendarEventResponse[]> {
    return this.http.get<CalendarEventResponse[]>(
      `${this.baseUrl}/academic-year/${academicYearId}`,
    );
  }

  create(req: CalendarEventRequest): Observable<CalendarEventResponse> {
    return this.http.post<CalendarEventResponse>(this.baseUrl, req);
  }

  update(id: number, req: CalendarEventRequest): Observable<CalendarEventResponse> {
    return this.http.put<CalendarEventResponse>(`${this.baseUrl}/${id}`, req);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
