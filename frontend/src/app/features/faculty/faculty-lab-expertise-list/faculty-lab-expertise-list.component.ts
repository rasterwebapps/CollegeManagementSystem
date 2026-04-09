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
  selector: 'app-faculty-lab-expertise-list',
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
      <h2>Faculty Lab Expertise</h2>
      <mat-table [dataSource]="facultyService.labExpertise()" class="faculty-table">
        <ng-container matColumnDef="facultyName">
          <mat-header-cell *matHeaderCellDef>Faculty</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.facultyName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="labTypeName">
          <mat-header-cell *matHeaderCellDef>Lab Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.labTypeName }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="proficiencyLevel">
          <mat-header-cell *matHeaderCellDef>Proficiency</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.proficiencyLevel }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="certifiedDate">
          <mat-header-cell *matHeaderCellDef>Certified</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.certifiedDate }}</mat-cell>
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
export class FacultyLabExpertiseListComponent implements OnInit {
  protected readonly facultyService = inject(FacultyService);
  readonly displayedColumns = [
    'facultyName',
    'labTypeName',
    'proficiencyLevel',
    'certifiedDate',
    'actions',
  ];

  ngOnInit(): void {
    this.facultyService.loadLabExpertise();
  }

  onDelete(id: number): void {
    this.facultyService
      .deleteLabExpertise(id)
      .subscribe(() => this.facultyService.loadLabExpertise());
  }
}
