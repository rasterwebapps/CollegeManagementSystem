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
  selector: 'app-payment-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header"><div>
        <h2 class="page-title">Payments</h2>
        <p class="page-subtitle">Student payment records</p>
      </div></div>
      <mat-table [dataSource]="financeService.payments()" class="data-table">
        <ng-container matColumnDef="studentName">
          <mat-header-cell *matHeaderCellDef>Student</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.studentName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="amount">
          <mat-header-cell *matHeaderCellDef>Amount</mat-header-cell>
          <mat-cell *matCellDef="let row">₹{{ row.amount }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="paymentDate">
          <mat-header-cell *matHeaderCellDef>Date</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.paymentDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="paymentMethod">
          <mat-header-cell *matHeaderCellDef>Method</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.paymentMethod }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="transactionId">
          <mat-header-cell *matHeaderCellDef>Transaction ID</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.transactionId }}</mat-cell>
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
export class PaymentListComponent implements OnInit {
  protected readonly financeService = inject(FinanceService);
  readonly displayedColumns = ['studentName', 'amount', 'paymentDate', 'paymentMethod', 'transactionId', 'status', 'actions'];

  ngOnInit(): void {
    this.financeService.loadPayments();
  }

  onDelete(id: number): void {
    this.financeService.deletePayment(id).subscribe(() => this.financeService.loadPayments());
  }
}
