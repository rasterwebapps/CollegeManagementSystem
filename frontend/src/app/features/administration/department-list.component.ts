import { Component, inject, signal, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatTooltip } from '@angular/material/tooltip';
import { DepartmentService } from '../../core/services/department.service';
import { DepartmentResponse } from '../../core/models/api.models';
import { DepartmentFormDialogComponent } from './department-form-dialog.component';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-department-list',
  standalone: true,
  imports: [MatTableModule, MatButton, MatIconButton, MatIcon, MatTooltip],
  templateUrl: './department-list.component.html',
  styleUrl: './department-list.component.scss',
})
export class DepartmentListComponent implements OnInit {
  private readonly service = inject(DepartmentService);
  private readonly dialog = inject(MatDialog);

  protected readonly items = signal<DepartmentResponse[]>([]);
  protected readonly displayedColumns = ['code', 'name', 'headName', 'active', 'actions'];

  ngOnInit(): void {
    this.loadData();
  }

  protected loadData(): void {
    this.service.findAll().subscribe((data) => this.items.set(data));
  }

  protected openDialog(item?: DepartmentResponse): void {
    const ref = this.dialog.open(DepartmentFormDialogComponent, {
      width: '500px',
      data: { department: item },
    });
    ref.afterClosed().subscribe((result) => {
      if (result) this.loadData();
    });
  }

  protected deleteItem(item: DepartmentResponse): void {
    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Delete Department', message: `Delete department "${item.name}"?` },
    });
    ref.afterClosed().subscribe((confirmed) => {
      if (confirmed) this.service.delete(item.id).subscribe(() => this.loadData());
    });
  }
}
