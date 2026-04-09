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
import { GrievanceService } from '../services/grievance.service';

@Component({
  selector: 'app-grievance-list',
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
  templateUrl: './grievance-list.component.html',
  styleUrl: './grievance-list.component.scss',
})
export class GrievanceListComponent implements OnInit {
  protected readonly service = inject(GrievanceService);
  readonly displayedColumns = ['ticketNumber', 'complainantName', 'category', 'subject', 'priority', 'status'];

  ngOnInit(): void {
    this.service.loadGrievances();
  }
}
