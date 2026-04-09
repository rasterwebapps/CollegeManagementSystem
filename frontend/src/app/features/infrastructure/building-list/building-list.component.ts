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
  selector: 'app-building-list',
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
  templateUrl: './building-list.component.html',
  styleUrl: './building-list.component.scss',
})
export class BuildingListComponent implements OnInit {
  protected readonly service = inject(InfrastructureService);
  readonly displayedColumns = ['name', 'code', 'floors', 'address', 'status'];

  ngOnInit(): void {
    this.service.loadBuildings();
  }
}
