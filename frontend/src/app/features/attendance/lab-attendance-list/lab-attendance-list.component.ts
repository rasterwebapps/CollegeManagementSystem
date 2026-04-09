import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { MatChip } from '@angular/material/chips';
import { AttendanceService } from '../services/attendance.service';

@Component({
  selector: 'app-lab-attendance-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Lab Attendance</h2>
        <p class="page-subtitle">Student lab session attendance records</p>
      </div></div>
      <mat-table [dataSource]="attendanceService.labAttendances()" class="data-table">
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labName">
          <mat-header-cell *matHeaderCellDef>Lab</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="attendanceDate">
          <mat-header-cell *matHeaderCellDef>Date</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.attendanceDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="status">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.status === 'PRESENT'">{{ row.status }}</mat-chip>
          </mat-cell>
        </ng-container>
        <ng-container matColumnDef="remarks">
          <mat-header-cell *matHeaderCellDef>Remarks</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.remarks }}</mat-cell>
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
export class LabAttendanceListComponent implements OnInit {
  protected readonly attendanceService = inject(AttendanceService);
  readonly displayedColumns = ['studentName', 'labName', 'attendanceDate', 'status', 'remarks', 'actions'];

  ngOnInit(): void {
    this.attendanceService.loadLabAttendances();
  }

  onDelete(id: number): void {
    this.attendanceService.deleteLabAttendance(id).subscribe(() => this.attendanceService.loadLabAttendances());
  }
}
