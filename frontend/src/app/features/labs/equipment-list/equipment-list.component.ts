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
  selector: 'app-equipment-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Equipment</h2>
        <p class="page-subtitle">Lab equipment inventory management</p>
      </div></div>
      <mat-table [dataSource]="labService.equipment()" class="data-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="serialNumber">
          <mat-header-cell *matHeaderCellDef>Serial #</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.serialNumber }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labName">
          <mat-header-cell *matHeaderCellDef>Lab</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="status">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.status === 'WORKING'">{{ row.status }}</mat-chip>
          </mat-cell>
        </ng-container>
        <ng-container matColumnDef="cost">
          <mat-header-cell *matHeaderCellDef>Cost</mat-header-cell>
          <mat-cell *matCellDef="let row">₹{{ row.cost }}</mat-cell>
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
export class EquipmentListComponent implements OnInit {
  protected readonly labService = inject(LabService);
  readonly displayedColumns = ['name', 'serialNumber', 'labName', 'status', 'cost', 'actions'];

  ngOnInit(): void {
    this.labService.loadEquipment();
  }

  onDelete(id: number): void {
    this.labService.deleteEquipment(id).subscribe(() => this.labService.loadEquipment());
  }
}
