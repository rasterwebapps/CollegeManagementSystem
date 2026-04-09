import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { AdminService, Department } from '../services/admin.service';

@Component({
  selector: 'app-department-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatButton,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatFormField,
    MatLabel,
    MatInput,
  ],
  template: `
    <h2 mat-dialog-title>{{ data ? 'Edit' : 'Add' }} Department</h2>
    <mat-dialog-content>
      <form [formGroup]="form">
        <mat-form-field class="full-width">
          <mat-label>Name</mat-label>
          <input matInput formControlName="name" />
        </mat-form-field>
        <mat-form-field class="full-width">
          <mat-label>Code</mat-label>
          <input matInput formControlName="code" />
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="dialogRef.close()">Cancel</button>
      <button mat-raised-button color="primary" [disabled]="form.invalid" (click)="save()">
        Save
      </button>
    </mat-dialog-actions>
  `,
  styles: ['.full-width { width: 100%; margin-bottom: 8px; }'],
})
export class DepartmentDialogComponent {
  readonly dialogRef = inject(MatDialogRef<DepartmentDialogComponent>);
  readonly data: Department | null = inject(MAT_DIALOG_DATA, { optional: true });
  private readonly adminService = inject(AdminService);
  private readonly fb = inject(FormBuilder);

  readonly form = this.fb.group({
    name: [this.data?.name ?? '', [Validators.required]],
    code: [this.data?.code ?? '', [Validators.required]],
  });

  save(): void {
    if (this.form.invalid) return;
    const value = this.form.getRawValue() as { name: string; code: string };
    if (this.data) {
      this.adminService.updateDepartment(this.data.id, value).subscribe(() => this.dialogRef.close(true));
    } else {
      this.adminService.createDepartment(value).subscribe(() => this.dialogRef.close(true));
    }
  }
}
