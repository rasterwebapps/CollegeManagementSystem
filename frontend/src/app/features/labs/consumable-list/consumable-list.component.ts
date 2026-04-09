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
  selector: 'app-consumable-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Consumable Stocks</h2>
        <p class="page-subtitle">Track consumable inventory levels</p>
      </div></div>
      <mat-table [dataSource]="labService.consumables()" class="data-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labName">
          <mat-header-cell *matHeaderCellDef>Lab</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="quantity">
          <mat-header-cell *matHeaderCellDef>Quantity</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.quantity }} {{ row.unit }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="reorderLevel">
          <mat-header-cell *matHeaderCellDef>Reorder Level</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.reorderLevel }}</mat-cell>
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
export class ConsumableListComponent implements OnInit {
  protected readonly labService = inject(LabService);
  readonly displayedColumns = ['name', 'labName', 'quantity', 'reorderLevel', 'actions'];

  ngOnInit(): void {
    this.labService.loadConsumables();
  }

  onDelete(id: number): void {
    this.labService.deleteConsumable(id).subscribe(() => this.labService.loadConsumables());
  }
}
