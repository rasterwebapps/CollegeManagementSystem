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
import { ExamService } from '../services/exams.service';

@Component({
  selector: 'app-exam-schedule-list',
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
  templateUrl: './exam-schedule-list.component.html',
  styleUrl: './exam-schedule-list.component.scss',
})
export class ExamScheduleListComponent implements OnInit {
  protected readonly service = inject(ExamService);
  readonly displayedColumns = ['examType', 'date', 'duration', 'maxMarks', 'status'];

  ngOnInit(): void {
    this.service.loadExamSchedules();
  }
}
