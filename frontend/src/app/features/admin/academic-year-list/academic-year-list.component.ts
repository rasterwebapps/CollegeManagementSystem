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
  selector: 'app-academic-year-list',
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
      <h2>Academic Years</h2>
      <mat-table [dataSource]="adminService.academicYears()" class="admin-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="startDate">
          <mat-header-cell *matHeaderCellDef>Start Date</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.startDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="endDate">
          <mat-header-cell *matHeaderCellDef>End Date</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.endDate }}</mat-cell>
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
  styles: ['.admin-container { padding: 24px; } .admin-table { width: 100%; }'],
})
export class AcademicYearListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  readonly displayedColumns = ['name', 'startDate', 'endDate', 'active', 'actions'];

  ngOnInit(): void {
    this.adminService.loadAcademicYears();
  }

  onDelete(id: number): void {
    this.adminService
      .deleteAcademicYear(id)
      .subscribe(() => this.adminService.loadAcademicYears());
  }
}
