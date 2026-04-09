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
  selector: 'app-program-list',
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
      <div class="admin-header">
        <h2>Programs</h2>
      </div>
      <mat-table [dataSource]="adminService.programs()" class="admin-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="code">
          <mat-header-cell *matHeaderCellDef>Code</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.code }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="departmentName">
          <mat-header-cell *matHeaderCellDef>Department</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.departmentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="degreeType">
          <mat-header-cell *matHeaderCellDef>Degree</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.degreeType }}</mat-cell>
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
    `
      .admin-container {
        padding: 24px;
      }
      .admin-header {
        margin-bottom: 16px;
      }
      .admin-table {
        width: 100%;
      }
    `,
  ],
})
export class ProgramListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  readonly displayedColumns = ['name', 'code', 'departmentName', 'degreeType', 'actions'];

  ngOnInit(): void {
    this.adminService.loadPrograms();
  }

  onDelete(id: number): void {
    this.adminService.deleteProgram(id).subscribe(() => this.adminService.loadPrograms());
  }
}
