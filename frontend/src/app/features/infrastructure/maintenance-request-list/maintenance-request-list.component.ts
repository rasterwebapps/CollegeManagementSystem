import { Component, inject, OnInit } from '@angular/core';
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
import { InfrastructureService } from '../services/infrastructure.service';

@Component({
  selector: 'app-maintenance-request-list',
  standalone: true,
  imports: [
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
  ],
  templateUrl: './maintenance-request-list.component.html',
  styleUrl: './maintenance-request-list.component.scss',
})
export class MaintenanceRequestListComponent implements OnInit {
  protected readonly service = inject(InfrastructureService);
  readonly displayedColumns = ['requesterName', 'location', 'priority', 'status', 'assignedTo'];

  ngOnInit(): void {
    this.service.loadMaintenanceRequests();
  }
}
