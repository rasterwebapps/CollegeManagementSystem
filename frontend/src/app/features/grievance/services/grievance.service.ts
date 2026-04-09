import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_GRIEVANCES,
  GET_GRIEVANCE_COMMENTS,
} from '../../../core/graphql/operations';

export interface Grievance {
  id: number;
  ticketNumber: string;
  complainantName: string;
  category: string;
  subject: string;
  priority: string;
  status: string;
}

export interface GrievanceComment {
  id: number;
  commenterName: string;
  comment: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class GrievanceService {
  private readonly apollo = inject(Apollo);

  readonly grievances = signal<Grievance[]>([]);
  readonly comments = signal<GrievanceComment[]>([]);

  loadGrievances() {
    this.apollo
      .watchQuery<{ grievances: Grievance[] }>({ query: GET_GRIEVANCES })
      .valueChanges.subscribe(({ data }) => this.grievances.set(data?.grievances ?? []));
  }

  loadComments(grievanceId: number) {
    this.apollo
      .watchQuery<{ grievanceComments: GrievanceComment[] }>({
        query: GET_GRIEVANCE_COMMENTS,
        variables: { grievanceId },
      })
      .valueChanges.subscribe(({ data }) =>
        this.comments.set(data?.grievanceComments ?? [])
      );
  }
}
