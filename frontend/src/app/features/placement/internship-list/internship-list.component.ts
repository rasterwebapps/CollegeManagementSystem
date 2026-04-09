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
import { PlacementService } from '../services/placement.service';

@Component({
  selector: 'app-internship-list',
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
  templateUrl: './internship-list.component.html',
  styleUrl: './internship-list.component.scss',
})
export class InternshipListComponent implements OnInit {
  protected readonly service = inject(PlacementService);
  readonly displayedColumns = ['role', 'startDate', 'endDate', 'stipend', 'status'];

  ngOnInit(): void {
    this.service.loadInternships();
  }
}
