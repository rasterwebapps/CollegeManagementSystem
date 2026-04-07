import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  ExperimentOutcomeMappingRequest,
  ExperimentOutcomeMappingResponse,
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ExperimentOutcomeMappingService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/experiment-outcome-mappings`;

  findByExperimentId(experimentId: number): Observable<ExperimentOutcomeMappingResponse[]> {
    return this.http.get<ExperimentOutcomeMappingResponse[]>(
      `${this.baseUrl}/experiment/${experimentId}`,
    );
  }

  create(
    req: ExperimentOutcomeMappingRequest,
  ): Observable<ExperimentOutcomeMappingResponse> {
    return this.http.post<ExperimentOutcomeMappingResponse>(this.baseUrl, req);
  }

  delete(experimentId: number, courseOutcomeId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/experiment/${experimentId}/outcome/${courseOutcomeId}`,
    );
  }
}
