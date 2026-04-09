import { Component, inject, OnInit } from '@angular/core';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
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
import { AdminService, Department } from '../services/admin.service';
import { DepartmentDialogComponent } from '../department-dialog/department-dialog.component';

@Component({
  selector: 'app-department-list',
  standalone: true,
  imports: [
    MatButton,
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
  templateUrl: './department-list.component.html',
  styleUrl: './department-list.component.scss',
})
export class DepartmentListComponent implements OnInit {
  protected readonly adminService = inject(AdminService);
  private readonly dialog = inject(MatDialog);
  readonly displayedColumns = ['name', 'code', 'actions'];

  ngOnInit(): void {
    this.adminService.loadDepartments();
  }

  openDialog(department?: Department): void {
    const dialogRef = this.dialog.open(DepartmentDialogComponent, {
      width: '400px',
      data: department,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.adminService.loadDepartments();
      }
    });
  }

  onDelete(id: number): void {
    this.adminService.deleteDepartment(id).subscribe(() => this.adminService.loadDepartments());
  }
}
