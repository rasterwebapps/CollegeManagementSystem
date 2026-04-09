import { Component, inject, OnInit } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef,
  MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { MatChip } from '@angular/material/chips';
import { StudentService } from '../services/student.service';

@Component({
  selector: 'app-student-profile-list',
  standalone: true,
  imports: [
    MatIconButton, MatIcon, MatTable, MatColumnDef,
    MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTooltip, MatChip,
  ],
  template: `
    <div class="page-container">
      <div class="page-header">
        <div>
          <h2 class="page-title">Student Profiles</h2>
          <p class="page-subtitle">Manage student records and information</p>
        </div>
      </div>

      <mat-table [dataSource]="studentService.profiles()" class="data-table">
        <ng-container matColumnDef="rollNumber">
          <mat-header-cell *matHeaderCellDef>Roll No.</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.rollNumber }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="name">
          <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.firstName }} {{ row.lastName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="email">
          <mat-header-cell *matHeaderCellDef>Email</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.email }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="programName">
          <mat-header-cell *matHeaderCellDef>Program</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.programName }}</mat-cell>
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
    .page-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 16px; }
    .page-title { font-size: 1.25rem; font-weight: 600; margin: 0; }
    .page-subtitle { font-size: 0.8125rem; color: var(--mat-sys-on-surface-variant); margin: 4px 0 0; }
    .data-table { width: 100%; }
  `],
})
export class StudentProfileListComponent implements OnInit {
  protected readonly studentService = inject(StudentService);
  readonly displayedColumns = ['rollNumber', 'name', 'email', 'programName', 'status', 'actions'];

  ngOnInit(): void {
    this.studentService.loadProfiles();
  }

  onDelete(id: number): void {
    this.studentService.deleteProfile(id).subscribe(() => this.studentService.loadProfiles());
  }
}
