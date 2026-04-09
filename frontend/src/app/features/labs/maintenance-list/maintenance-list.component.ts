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
  selector: 'app-maintenance-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Maintenance Schedules</h2>
        <p class="page-subtitle">Equipment maintenance tracking</p>
      </div></div>
      <mat-table [dataSource]="labService.maintenanceSchedules()" class="data-table">
        <ng-container matColumnDef="equipmentName">
          <mat-header-cell *matHeaderCellDef>Equipment</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.equipmentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="maintenanceType">
          <mat-header-cell *matHeaderCellDef>Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.maintenanceType }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="scheduledDate">
          <mat-header-cell *matHeaderCellDef>Scheduled</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.scheduledDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="status">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.status === 'COMPLETED'">{{ row.status }}</mat-chip>
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
export class MaintenanceListComponent implements OnInit {
  protected readonly labService = inject(LabService);
  readonly displayedColumns = ['equipmentName', 'maintenanceType', 'scheduledDate', 'status', 'actions'];

  ngOnInit(): void {
    this.labService.loadMaintenanceSchedules();
  }

  onDelete(id: number): void {
    this.labService.deleteMaintenanceSchedule(id).subscribe(() => this.labService.loadMaintenanceSchedules());
  }
}
