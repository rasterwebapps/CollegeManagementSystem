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
  selector: 'app-attendance-alert-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Attendance Alerts</h2>
        <p class="page-subtitle">Low attendance notifications</p>
      </div></div>
      <mat-table [dataSource]="attendanceService.alerts()" class="data-table">
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="courseName">
          <mat-header-cell *matHeaderCellDef>Course</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.courseName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="alertType">
          <mat-header-cell *matHeaderCellDef>Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.alertType }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="message">
          <mat-header-cell *matHeaderCellDef>Message</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.message }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="resolved">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.resolved">{{ row.resolved ? 'Resolved' : 'Pending' }}</mat-chip>
          </mat-cell>
        </ng-container>
        <ng-container matColumnDef="actions">
          <mat-header-cell *matHeaderCellDef>Actions</mat-header-cell>
          <mat-cell *matCellDef="let row">
            @if (!row.resolved) {
              <button mat-icon-button color="primary" (click)="onResolve(row.id)" matTooltip="Resolve">
                <mat-icon>check_circle</mat-icon>
              </button>
            }
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
export class AttendanceAlertListComponent implements OnInit {
  protected readonly attendanceService = inject(AttendanceService);
  readonly displayedColumns = ['studentName', 'courseName', 'alertType', 'message', 'resolved', 'actions'];

  ngOnInit(): void {
    this.attendanceService.loadAlerts();
  }

  onResolve(id: number): void {
    this.attendanceService.resolveAlert(id).subscribe(() => this.attendanceService.loadAlerts());
  }

  onDelete(id: number): void {
    this.attendanceService.deleteAlert(id).subscribe(() => this.attendanceService.loadAlerts());
  }
}
