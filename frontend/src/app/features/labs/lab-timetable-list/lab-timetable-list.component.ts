import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { LabService } from '../services/lab.service';

@Component({
  selector: 'app-lab-timetable-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Lab Timetable</h2>
        <p class="page-subtitle">Lab session schedules</p>
      </div></div>
      <mat-table [dataSource]="labService.timetables()" class="data-table">
        <ng-container matColumnDef="labName">
          <mat-header-cell *matHeaderCellDef>Lab</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="courseName">
          <mat-header-cell *matHeaderCellDef>Course</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.courseName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="dayOfWeek">
          <mat-header-cell *matHeaderCellDef>Day</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.dayOfWeek }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="startTime">
          <mat-header-cell *matHeaderCellDef>Start</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.startTime }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="endTime">
          <mat-header-cell *matHeaderCellDef>End</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.endTime }}</mat-cell>
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
export class LabTimetableListComponent implements OnInit {
  protected readonly labService = inject(LabService);
  readonly displayedColumns = ['labName', 'courseName', 'dayOfWeek', 'startTime', 'endTime', 'actions'];

  ngOnInit(): void {
    this.labService.loadTimetables();
  }

  onDelete(id: number): void {
    this.labService.deleteTimetable(id).subscribe(() => this.labService.loadTimetables());
  }
}
