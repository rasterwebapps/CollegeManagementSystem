import { Component, inject, OnInit } from '@angular/core';
import {
  MatTable,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatCell,
  MatCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
} from '@angular/material/table';
import { FeedbackService } from '../services/feedback.service';

@Component({
  selector: 'app-feedback-form-list',
  standalone: true,
  imports: [
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
  ],
  templateUrl: './feedback-form-list.component.html',
  styleUrl: './feedback-form-list.component.scss',
})
export class FeedbackFormListComponent implements OnInit {
  protected readonly service = inject(FeedbackService);
  readonly displayedColumns = ['title', 'targetAudience', 'startDate', 'endDate', 'status'];

  ngOnInit(): void {
    this.service.loadForms();
  }
}
