import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { GET_FEEDBACK_FORMS, GET_FEEDBACK_RESPONSES } from '../../../core/graphql/operations';

export interface FeedbackForm {
  id: number;
  title: string;
  targetAudience: string;
  startDate: string;
  endDate: string;
  status: string;
}

export interface FeedbackResponse {
  id: number;
  respondentKeycloakId: string;
  answers: string;
  submittedAt: string;
}

@Injectable({ providedIn: 'root' })
export class FeedbackService {
  private readonly apollo = inject(Apollo);

  readonly forms = signal<FeedbackForm[]>([]);
  readonly responses = signal<FeedbackResponse[]>([]);

  loadForms() {
    this.apollo
      .watchQuery<{ feedbackForms: FeedbackForm[] }>({ query: GET_FEEDBACK_FORMS })
      .valueChanges.subscribe(({ data }) => this.forms.set(data?.feedbackForms ?? []));
  }

  loadResponses(formId: number) {
    this.apollo
      .watchQuery<{ feedbackResponses: FeedbackResponse[] }>({
        query: GET_FEEDBACK_RESPONSES,
        variables: { formId },
      })
      .valueChanges.subscribe(({ data }) =>
        this.responses.set(data?.feedbackResponses ?? [])
      );
  }
}
