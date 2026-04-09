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
  selector: 'app-drive-list',
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
  templateUrl: './drive-list.component.html',
  styleUrl: './drive-list.component.scss',
})
export class DriveListComponent implements OnInit {
  protected readonly service = inject(PlacementService);
  readonly displayedColumns = ['title', 'driveDate', 'rolesOffered', 'packageMin', 'status'];

  ngOnInit(): void {
    this.service.loadDrives();
  }
}
