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
import { HostelService } from '../services/hostel.service';

@Component({
  selector: 'app-hostel-list',
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
  templateUrl: './hostel-list.component.html',
  styleUrl: './hostel-list.component.scss',
})
export class HostelListComponent implements OnInit {
  protected readonly service = inject(HostelService);
  readonly displayedColumns = ['name', 'code', 'hostelType', 'totalCapacity', 'status'];

  ngOnInit(): void {
    this.service.loadHostels();
  }
}
