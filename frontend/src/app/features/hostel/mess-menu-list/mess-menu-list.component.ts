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
  selector: 'app-mess-menu-list',
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
  templateUrl: './mess-menu-list.component.html',
  styleUrl: './mess-menu-list.component.scss',
})
export class MessMenuListComponent implements OnInit {
  protected readonly service = inject(HostelService);
  readonly displayedColumns = ['dayOfWeek', 'mealType', 'items'];

  ngOnInit(): void {
    this.service.loadMessMenus(1);
  }
}
