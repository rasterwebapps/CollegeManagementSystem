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
  selector: 'app-grade-scale-list',
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
  templateUrl: './grade-scale-list.component.html',
  styleUrl: './grade-scale-list.component.scss',
})
export class GradeScaleListComponent implements OnInit {
  protected readonly service = inject(ExamService);
  readonly displayedColumns = ['grade', 'minPercentage', 'maxPercentage', 'gradePoint'];

  ngOnInit(): void {
    this.service.loadGradeScales();
  }
}
