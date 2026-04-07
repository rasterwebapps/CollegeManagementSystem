import { Component, inject, signal, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatProgressBar } from '@angular/material/progress-bar';
import { AuthService } from '../../core/auth/auth.service';

interface LabUtilizationData {
  labName: string;
  type: string;
  utilizationPercent: number;
  status: 'available' | 'in-use' | 'maintenance';
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    MatIcon,
    MatProgressBar,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  protected readonly authService = inject(AuthService);

  protected readonly labUtilizationData = signal<LabUtilizationData[]>([
    { labName: 'Computer Lab A', type: 'Computer', utilizationPercent: 85, status: 'in-use' },
    { labName: 'Physics Lab 1', type: 'Physics', utilizationPercent: 60, status: 'in-use' },
    { labName: 'Chemistry Lab 2', type: 'Chemistry', utilizationPercent: 0, status: 'maintenance' },
    { labName: 'Computer Lab B', type: 'Computer', utilizationPercent: 45, status: 'in-use' },
    { labName: 'Electronics Lab', type: 'Electronics', utilizationPercent: 0, status: 'available' },
    { labName: 'Biology Lab', type: 'Biology', utilizationPercent: 72, status: 'in-use' },
  ]);

  protected readonly summaryCards = signal([
    { title: 'Total Labs', value: '24', icon: 'science', color: 'primary' },
    { title: 'In Use', value: '18', icon: 'play_circle', color: 'accent' },
    { title: 'Available', value: '4', icon: 'check_circle', color: 'green' },
    { title: 'Maintenance', value: '2', icon: 'build', color: 'warn' },
  ]);

  ngOnInit(): void {
    // In a real app, this would fetch data from the API
  }

  protected getStatusColor(status: string): string {
    return status === 'in-use'
      ? 'primary'
      : status === 'maintenance'
        ? 'warn'
        : 'accent';
  }

  protected getStatusIcon(status: string): string {
    return status === 'in-use'
      ? 'play_circle'
      : status === 'maintenance'
        ? 'build'
        : 'check_circle';
  }
}
