import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
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
import { MatTooltip } from '@angular/material/tooltip';
import { FacultyService } from '../services/faculty.service';

@Component({
  selector: 'app-faculty-lab-assignment-list',
  standalone: true,
  imports: [
    MatIconButton,
    MatIcon,
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
    MatTooltip,
  ],
  template: `
    <div class="faculty-container">
      <h2>Faculty Lab Assignments</h2>
      <mat-table [dataSource]="facultyService.labAssignments()" class="faculty-table">
        <ng-container matColumnDef="facultyName">
          <mat-header-cell *matHeaderCellDef>Faculty</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.facultyName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labName">
          <mat-header-cell *matHeaderCellDef>Lab</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="semesterName">
          <mat-header-cell *matHeaderCellDef>Semester</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.semesterName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="assignedDate">
          <mat-header-cell *matHeaderCellDef>Assigned</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.assignedDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="active">
          <mat-header-cell *matHeaderCellDef>Active</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.active ? 'Yes' : 'No' }}</mat-cell>
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
  styles: ['.faculty-container { padding: 24px; } .faculty-table { width: 100%; }'],
})
export class FacultyLabAssignmentListComponent implements OnInit {
  protected readonly facultyService = inject(FacultyService);
  readonly displayedColumns = [
    'facultyName',
    'labName',
    'semesterName',
    'assignedDate',
    'active',
    'actions',
  ];

  ngOnInit(): void {
    this.facultyService.loadLabAssignments();
  }

  onDelete(id: number): void {
    this.facultyService
      .deleteLabAssignment(id)
      .subscribe(() => this.facultyService.loadLabAssignments());
  }
}
