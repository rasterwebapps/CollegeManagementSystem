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
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-semester-list',
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
    <div class="admin-container">
      <h2>Semesters</h2>
      <mat-table [dataSource]="adminService.semesters()" class="admin-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="semesterNumber">
          <mat-header-cell *matHeaderCellDef>#</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.semesterNumber }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="academicYearName">
          <mat-header-cell *matHeaderCellDef>Academic Year</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.academicYearName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="startDate">
          <mat-header-cell *matHeaderCellDef>Start</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.startDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="endDate">
          <mat-header-cell *matHeaderCellDef>End</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.endDate }}</mat-cell>
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
  styles: ['.admin-container { padding: 24px; } .admin-table { width: 100%; }'],
})
export class SemesterListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  readonly displayedColumns = [
    'name',
    'semesterNumber',
    'academicYearName',
    'startDate',
    'endDate',
    'actions',
  ];

  ngOnInit(): void {
    this.adminService.loadSemesters();
  }

  onDelete(id: number): void {
    this.adminService.deleteSemester(id).subscribe(() => this.adminService.loadSemesters());
  }
}
