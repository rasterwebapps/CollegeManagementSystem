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
  selector: 'app-faculty-profile-list',
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
      <h2>Faculty Profiles</h2>
      <mat-table [dataSource]="facultyService.profiles()" class="faculty-table">
        <ng-container matColumnDef="employeeCode">
          <mat-header-cell *matHeaderCellDef>Employee Code</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.employeeCode }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.firstName }} {{ row.lastName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="email">
          <mat-header-cell *matHeaderCellDef>Email</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.email }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="departmentName">
          <mat-header-cell *matHeaderCellDef>Department</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.departmentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="designation">
          <mat-header-cell *matHeaderCellDef>Designation</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.designation }}</mat-cell>
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
export class FacultyProfileListComponent implements OnInit {
  protected readonly facultyService = inject(FacultyService);
  readonly displayedColumns = [
    'employeeCode',
    'name',
    'email',
    'departmentName',
    'designation',
    'actions',
  ];

  ngOnInit(): void {
    this.facultyService.loadProfiles();
  }

  onDelete(id: number): void {
    this.facultyService.deleteProfile(id).subscribe(() => this.facultyService.loadProfiles());
  }
}
