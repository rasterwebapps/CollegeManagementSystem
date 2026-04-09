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
  selector: 'app-facility-list',
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
  templateUrl: './facility-list.component.html',
  styleUrl: './facility-list.component.scss',
})
export class FacilityListComponent implements OnInit {
  protected readonly service = inject(InfrastructureService);
  readonly displayedColumns = ['name', 'type', 'capacity', 'location', 'status'];

  ngOnInit(): void {
    this.service.loadFacilities();
  }
}
