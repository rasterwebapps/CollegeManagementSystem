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
  selector: 'app-course-list',
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
      <h2>Courses</h2>
      <mat-table [dataSource]="adminService.courses()" class="admin-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="code">
          <mat-header-cell *matHeaderCellDef>Code</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.code }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="programName">
          <mat-header-cell *matHeaderCellDef>Program</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.programName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="credits">
          <mat-header-cell *matHeaderCellDef>Credits</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.credits }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="courseType">
          <mat-header-cell *matHeaderCellDef>Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.courseType }}</mat-cell>
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
  styles: [
    '.admin-container { padding: 24px; } .admin-table { width: 100%; }',
  ],
})
export class CourseListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  readonly displayedColumns = ['name', 'code', 'programName', 'credits', 'courseType', 'actions'];

  ngOnInit(): void {
    this.adminService.loadCourses();
  }

  onDelete(id: number): void {
    this.adminService.deleteCourse(id).subscribe(() => this.adminService.loadCourses());
  }
}
