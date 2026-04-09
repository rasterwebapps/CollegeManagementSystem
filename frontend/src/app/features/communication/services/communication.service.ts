import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_ANNOUNCEMENTS,
  GET_GRIEVANCES,
  GET_MY_GRIEVANCES,
  GET_NOTIFICATIONS,
  GET_MESSAGES,
  CREATE_ANNOUNCEMENT,
  CREATE_GRIEVANCE,
} from '../../../core/graphql/operations';

export interface Announcement {
  id: number;
  title: string;
  content: string;
  category: string;
  priority: string;
  publishDate: string;
  expiryDate: string;
  pinned: boolean;
  publishedBy: string;
  status: string;
}

export interface Grievance {
  id: number;
  ticketNumber: string;
  complainantName: string;
  category: string;
  subject: string;
  description?: string;
  priority: string;
  status: string;
  resolution?: string;
  createdAt: string;
}

export interface Notification {
  id: number;
  title: string;
  message: string;
  type: string;
  readStatus: boolean;
  link: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class CommunicationService {
  private readonly apollo = inject(Apollo);

  readonly announcements = signal<Announcement[]>([]);
  readonly grievances = signal<Grievance[]>([]);
  readonly myGrievances = signal<Grievance[]>([]);
  readonly notifications = signal<Notification[]>([]);

  loadAnnouncements() {
    this.apollo
      .watchQuery<{ announcements: Announcement[] }>({ query: GET_ANNOUNCEMENTS })
      .valueChanges.subscribe(({ data }) => this.announcements.set((data?.announcements ?? []) as Announcement[]));
  }

  loadGrievances() {
    this.apollo
      .watchQuery<{ grievances: Grievance[] }>({ query: GET_GRIEVANCES })
      .valueChanges.subscribe(({ data }) => this.grievances.set((data?.grievances ?? []) as Grievance[]));
  }

  loadMyGrievances(keycloakId: string) {
    this.apollo
      .watchQuery<{ myGrievances: Grievance[] }>({
        query: GET_MY_GRIEVANCES,
        variables: { keycloakId },
      })
      .valueChanges.subscribe(({ data }) => this.myGrievances.set((data?.myGrievances ?? []) as Grievance[]));
  }

  createAnnouncement(input: Partial<Announcement>) {
    return this.apollo.mutate({
      mutation: CREATE_ANNOUNCEMENT,
      variables: { input },
      refetchQueries: [{ query: GET_ANNOUNCEMENTS }],
    });
  }

  createGrievance(input: {
    complainantKeycloakId: string;
    complainantName: string;
    complainantRole: string;
    category: string;
    subject: string;
    description: string;
    priority?: string;
    isAnonymous?: boolean;
  }) {
    return this.apollo.mutate({
      mutation: CREATE_GRIEVANCE,
      variables: { input },
      refetchQueries: [{ query: GET_GRIEVANCES }],
    });
  }

  loadNotifications(recipientId: string) {
    this.apollo
      .watchQuery<{ notifications: Notification[] }>({
        query: GET_NOTIFICATIONS,
        variables: { recipientId },
      })
      .valueChanges.subscribe(({ data }) =>
        this.notifications.set(data?.notifications ?? [])
      );
  }
}
