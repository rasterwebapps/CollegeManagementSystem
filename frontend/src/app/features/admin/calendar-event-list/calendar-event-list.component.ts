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
  selector: 'app-calendar-event-list',
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
      <h2>Calendar Events</h2>
      <mat-table [dataSource]="adminService.calendarEvents()" class="admin-table">
        <ng-container matColumnDef="title">
          <mat-header-cell *matHeaderCellDef>Title</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.title }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="eventDate">
          <mat-header-cell *matHeaderCellDef>Date</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.eventDate }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="eventType">
          <mat-header-cell *matHeaderCellDef>Type</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.eventType }}</mat-cell>
        </ng-container>
        <ng-container matColumnDef="academicYearName">
          <mat-header-cell *matHeaderCellDef>Academic Year</mat-header-cell>
          <mat-cell *matCellDef="let row">{{ row.academicYearName }}</mat-cell>
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
export class CalendarEventListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  readonly displayedColumns = ['title', 'eventDate', 'eventType', 'academicYearName', 'actions'];

  ngOnInit(): void {
    this.adminService.loadCalendarEvents();
  }

  onDelete(id: number): void {
    this.adminService
      .deleteCalendarEvent(id)
      .subscribe(() => this.adminService.loadCalendarEvents());
  }
}
