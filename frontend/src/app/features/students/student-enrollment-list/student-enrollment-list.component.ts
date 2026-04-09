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
  selector: 'app-student-enrollment-list',
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
          <h2 class="page-title">Student Enrollments</h2>
          <p class="page-subtitle">Course enrollment records</p>
        </div>
      </div>
      <mat-table [dataSource]="studentService.enrollments()" class="data-table">
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="courseName">
          <mat-header-cell *matHeaderCellDef>Course</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.courseName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="semesterName">
          <mat-header-cell *matHeaderCellDef>Semester</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.semesterName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="enrollmentDate">
          <mat-header-cell *matHeaderCellDef>Enrolled On</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.enrollmentDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="status">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.status }}</mat-cell>
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
export class StudentEnrollmentListComponent implements OnInit {
  protected readonly studentService = inject(StudentService);
  readonly displayedColumns = ['studentName', 'courseName', 'semesterName', 'enrollmentDate', 'status', 'actions'];

  ngOnInit(): void {
    this.studentService.loadEnrollments();
  }

  onDelete(id: number): void {
    this.studentService.deleteEnrollment(id).subscribe(() => this.studentService.loadEnrollments());
  }
}
