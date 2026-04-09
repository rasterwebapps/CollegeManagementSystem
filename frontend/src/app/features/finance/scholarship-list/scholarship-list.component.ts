import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { MatChip } from '@angular/material/chips';
import { FinanceService } from '../services/finance.service';

@Component({
  selector: 'app-scholarship-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Scholarships</h2>
        <p class="page-subtitle">Student scholarship awards</p>
      </div></div>
      <mat-table [dataSource]="financeService.scholarships()" class="data-table">
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.name }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="amount">
          <mat-header-cell *matHeaderCellDef>Amount</mat-header-cell>
          <mat-cell *matCellDef="let row">₹{{ row.amount }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="academicYearName">
          <mat-header-cell *matHeaderCellDef>Academic Year</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.academicYearName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="status">
          <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
          <mat-cell *matCellDef="let row">
            <mat-chip [highlighted]="row.status === 'ACTIVE'">{{ row.status }}</mat-chip>
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
export class ScholarshipListComponent implements OnInit {
  protected readonly financeService = inject(FinanceService);
  readonly displayedColumns = ['name', 'studentName', 'amount', 'academicYearName', 'status', 'actions'];

  ngOnInit(): void {
    this.financeService.loadScholarships();
  }

  onDelete(id: number): void {
    this.financeService.deleteScholarship(id).subscribe(() => this.financeService.loadScholarships());
  }
}
