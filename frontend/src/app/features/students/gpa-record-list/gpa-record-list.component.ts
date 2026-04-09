import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { StudentService } from '../services/student.service';

@Component({
  selector: 'app-gpa-record-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header">
        <div>
          <h2 class="page-title">GPA Records</h2>
          <p class="page-subtitle">Student academic performance records</p>
        </div>
      </div>
      <mat-table [dataSource]="studentService.gpaRecords()" class="data-table">
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="semesterName">
          <mat-header-cell *matHeaderCellDef>Semester</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.semesterName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="sgpa">
          <mat-header-cell *matHeaderCellDef>SGPA</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.sgpa }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="cgpa">
          <mat-header-cell *matHeaderCellDef>CGPA</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.cgpa }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="totalCredits">
          <mat-header-cell *matHeaderCellDef>Credits</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.totalCredits }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="actions">
          <mat-header-cell *matHeaderCellDef>Actions</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <button mat-icon-button color="warn" (click)="onDelete(row.id)" matTooltip="Delete">
              <mat-icon>delete</mat-icon>
            </button>
          </mat-cell>
        </ng-container>
        <mat-header-row *matHeaderRowDef="displayedColumns" />
        <mat-row *matRowDef="let row; columns: displayedColumns" />
      </mat-table>
    </div>
  `,
  styles: [`
    .page-container { padding: 24px; }
    .page-header { margin-bottom: 16px; }
    .page-title { font-size: 1.25rem; font-weight: 600; margin: 0; }
    .page-subtitle { font-size: 0.8125rem; color: var(--mat-sys-on-surface-variant); margin: 4px 0 0; }
    .data-table { width: 100%; }
  `],
})
export class GpaRecordListComponent implements OnInit {
  protected readonly studentService = inject(StudentService);
  readonly displayedColumns = ['studentName', 'semesterName', 'sgpa', 'cgpa', 'totalCredits', 'actions'];

  ngOnInit(): void {
    this.studentService.loadGpaRecords();
  }

  onDelete(id: number): void {
    this.studentService.deleteGpaRecord(id).subscribe(() => this.studentService.loadGpaRecords());
  }
}
