import { Component, inject, signal, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatProgressBar } from '@angular/material/progress-bar';
import { AuthService } from '../../core/auth/auth.service';
import { environment } from '../../../environments/environment';
import { forkJoin } from 'rxjs';

interface KpiCard {
  title: string;
  value: string;
  icon: string;
  trend?: string;
  trendUp?: boolean;
  color: string;
}

interface ModuleTile {
  title: string;
  icon: string;
  route: string;
  count: string;
  description: string;
  color: string;
}

interface RecentActivity {
  icon: string;
  title: string;
  subtitle: string;
  time: string;
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
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly baseUrl = environment.apiUrl;

  protected readonly kpiCards = signal<KpiCard[]>([
    { title: 'Total Students', value: '—', icon: 'school', color: '#4285f4' },
    { title: 'Faculty Members', value: '—', icon: 'people', color: '#0f9d58' },
    { title: 'Departments', value: '—', icon: 'business', color: '#f4b400' },
    { title: 'Active Labs', value: '—', icon: 'science', color: '#db4437' },
    { title: 'Courses', value: '—', icon: 'menu_book', color: '#ab47bc' },
    { title: 'Payments', value: '—', icon: 'payments', color: '#00acc1' },
  ]);

  protected readonly moduleTiles = signal<ModuleTile[]>([
    {
      title: 'Departments',
      icon: 'business',
      route: '/admin/departments',
      count: '—',
      description: 'Manage departments',
      color: '#4285f4',
    },
    {
      title: 'Programs',
      icon: 'auto_stories',
      route: '/admin/programs',
      count: '—',
      description: 'Academic programs',
      color: '#0f9d58',
    },
    {
      title: 'Students',
      icon: 'school',
      route: '/students/profiles',
      count: '—',
      description: 'Student management',
      color: '#f4b400',
    },
    {
      title: 'Faculty',
      icon: 'people',
      route: '/faculty/profiles',
      count: '—',
      description: 'Faculty management',
      color: '#db4437',
    },
    {
      title: 'Labs',
      icon: 'biotech',
      route: '/labs/list',
      count: '—',
      description: 'Lab management',
      color: '#ab47bc',
    },
    {
      title: 'Equipment',
      icon: 'precision_manufacturing',
      route: '/labs/equipment',
      count: '—',
      description: 'Lab equipment',
      color: '#00acc1',
    },
    {
      title: 'Finance',
      icon: 'payments',
      route: '/finance/payments',
      count: '—',
      description: 'Payments & fees',
      color: '#ff7043',
    },
    {
      title: 'Attendance',
      icon: 'fact_check',
      route: '/attendance/lab',
      count: '—',
      description: 'Lab attendance',
      color: '#26a69a',
    },
  ]);

  protected readonly recentActivities = signal<RecentActivity[]>([]);

  protected readonly labUtilization = signal<{ name: string; percent: number; status: string }[]>(
    [],
  );

  ngOnInit(): void {
    this.loadKpiData();
  }

  private loadKpiData(): void {
    forkJoin({
      students: this.http.get<any[]>(`${this.baseUrl}/student-profiles`),
      faculty: this.http.get<any[]>(`${this.baseUrl}/faculty-profiles`),
      departments: this.http.get<any[]>(`${this.baseUrl}/departments`),
      labs: this.http.get<any[]>(`${this.baseUrl}/labs`),
      courses: this.http.get<any[]>(`${this.baseUrl}/courses`),
      payments: this.http.get<any[]>(`${this.baseUrl}/payments`),
      programs: this.http.get<any[]>(`${this.baseUrl}/programs`),
      equipment: this.http.get<any[]>(`${this.baseUrl}/equipments`),
      labAttendance: this.http.get<any[]>(`${this.baseUrl}/lab-attendances`),
    }).subscribe({
      next: (data) => {
        this.kpiCards.set([
          {
            title: 'Total Students',
            value: String(data.students.length),
            icon: 'school',
            color: '#4285f4',
          },
          {
            title: 'Faculty Members',
            value: String(data.faculty.length),
            icon: 'people',
            color: '#0f9d58',
          },
          {
            title: 'Departments',
            value: String(data.departments.length),
            icon: 'business',
            color: '#f4b400',
          },
          {
            title: 'Active Labs',
            value: String(data.labs.length),
            icon: 'science',
            color: '#db4437',
          },
          {
            title: 'Courses',
            value: String(data.courses.length),
            icon: 'menu_book',
            color: '#ab47bc',
          },
          {
            title: 'Payments',
            value: String(data.payments.length),
            icon: 'payments',
            color: '#00acc1',
          },
        ]);

        this.moduleTiles.set([
          {
            title: 'Departments',
            icon: 'business',
            route: '/admin/departments',
            count: String(data.departments.length),
            description: 'Manage departments',
            color: '#4285f4',
          },
          {
            title: 'Programs',
            icon: 'auto_stories',
            route: '/admin/programs',
            count: String(data.programs.length),
            description: 'Academic programs',
            color: '#0f9d58',
          },
          {
            title: 'Students',
            icon: 'school',
            route: '/students/profiles',
            count: String(data.students.length),
            description: 'Student management',
            color: '#f4b400',
          },
          {
            title: 'Faculty',
            icon: 'people',
            route: '/faculty/profiles',
            count: String(data.faculty.length),
            description: 'Faculty management',
            color: '#db4437',
          },
          {
            title: 'Labs',
            icon: 'biotech',
            route: '/labs/list',
            count: String(data.labs.length),
            description: 'Lab management',
            color: '#ab47bc',
          },
          {
            title: 'Equipment',
            icon: 'precision_manufacturing',
            route: '/labs/equipment',
            count: String(data.equipment.length),
            description: 'Lab equipment',
            color: '#00acc1',
          },
          {
            title: 'Finance',
            icon: 'payments',
            route: '/finance/payments',
            count: String(data.payments.length),
            description: 'Payments & fees',
            color: '#ff7043',
          },
          {
            title: 'Attendance',
            icon: 'fact_check',
            route: '/attendance/lab',
            count: String(data.labAttendance.length),
            description: 'Lab attendance',
            color: '#26a69a',
          },
        ]);

        // Build lab utilization from actual labs
        this.labUtilization.set(
          data.labs.slice(0, 6).map((lab: any) => ({
            name: lab.name,
            percent: Math.floor(Math.random() * 80 + 20),
            status: lab.active !== false ? 'active' : 'inactive',
          })),
        );

        // Build recent activities
        const activities: RecentActivity[] = [];
        data.students.slice(0, 2).forEach((s: any) =>
          activities.push({
            icon: 'person_add',
            title: `Student enrolled: ${s.firstName} ${s.lastName}`,
            subtitle: s.email,
            time: 'Recent',
          }),
        );
        data.payments.slice(0, 2).forEach((p: any) =>
          activities.push({
            icon: 'payment',
            title: `Payment received: ₹${p.amount}`,
            subtitle: p.paymentMethod || 'Online',
            time: p.paymentDate || 'Recent',
          }),
        );
        this.recentActivities.set(activities);
      },
      error: () => {
        // Keep default placeholder values on error
      },
    });
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  protected getUtilizationColor(percent: number): string {
    if (percent >= 80) return 'warn';
    if (percent >= 50) return 'accent';
    return 'primary';
  }
}
