import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { MatChip } from '@angular/material/chips';
import { LabService } from '../services/lab.service';

@Component({
  selector: 'app-lab-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Labs</h2>
        <p class="page-subtitle">Manage laboratory facilities</p>
      </div></div>
      <mat-table [dataSource]="labService.labs()" class="data-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="code">
          <mat-header-cell *matHeaderCellDef>Code</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.code }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labTypeName">
          <mat-header-cell *matHeaderCellDef>Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labTypeName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="departmentName">
          <mat-header-cell *matHeaderCellDef>Department</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.departmentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="capacity">
          <mat-header-cell *matHeaderCellDef>Capacity</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.capacity }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="active">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.active">{{ row.active ? 'Active' : 'Inactive' }}</mat-chip>
          </mat-cell>
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
export class LabListComponent implements OnInit {
  protected readonly labService = inject(LabService);
  readonly displayedColumns = ['name', 'code', 'labTypeName', 'departmentName', 'capacity', 'active', 'actions'];

  ngOnInit(): void {
    this.labService.loadLabs();
  }

  onDelete(id: number): void {
    this.labService.deleteLab(id).subscribe(() => this.labService.loadLabs());
  }
}
