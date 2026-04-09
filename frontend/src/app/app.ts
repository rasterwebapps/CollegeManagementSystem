import { Component, inject, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { ThemeToggleComponent } from './shared/components/theme-toggle/theme-toggle.component';
import { AuthService } from './core/auth/auth.service';

interface NavItem {
  icon: string;
  label: string;
  route: string;
  children?: NavItem[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatIcon,
    MatIconButton,
    MatTooltip,
    ThemeToggleComponent,
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly authService = inject(AuthService);
  protected sidebarCollapsed = signal(false);
  protected expandedGroup = signal<string | null>(null);

  protected readonly navItems: NavItem[] = [
    { icon: 'dashboard', label: 'Dashboard', route: '/' },
    {
      icon: 'admin_panel_settings',
      label: 'Administration',
      route: '/admin',
      children: [
        { icon: 'business', label: 'Departments', route: '/admin/departments' },
        { icon: 'auto_stories', label: 'Programs', route: '/admin/programs' },
        { icon: 'menu_book', label: 'Courses', route: '/admin/courses' },
        { icon: 'date_range', label: 'Academic Years', route: '/admin/academic-years' },
        { icon: 'school', label: 'Semesters', route: '/admin/semesters' },
        { icon: 'event', label: 'Calendar Events', route: '/admin/calendar-events' },
      ],
    },
    {
      icon: 'people',
      label: 'Faculty',
      route: '/faculty',
      children: [
        { icon: 'person', label: 'Profiles', route: '/faculty/profiles' },
        { icon: 'science', label: 'Lab Expertise', route: '/faculty/lab-expertise' },
        { icon: 'assignment', label: 'Lab Assignments', route: '/faculty/lab-assignments' },
      ],
    },
    {
      icon: 'school',
      label: 'Students',
      route: '/students',
      children: [
        { icon: 'badge', label: 'Profiles', route: '/students/profiles' },
        { icon: 'how_to_reg', label: 'Enrollments', route: '/students/enrollments' },
        { icon: 'emoji_events', label: 'GPA Records', route: '/students/gpa-records' },
        { icon: 'door_front', label: 'Admissions', route: '/students/admissions' },
      ],
    },
    {
      icon: 'biotech',
      label: 'Labs',
      route: '/labs',
      children: [
        { icon: 'science', label: 'All Labs', route: '/labs/list' },
        { icon: 'category', label: 'Lab Types', route: '/labs/types' },
        { icon: 'precision_manufacturing', label: 'Equipment', route: '/labs/equipment' },
        { icon: 'schedule', label: 'Timetable', route: '/labs/timetable' },
        { icon: 'inventory_2', label: 'Consumables', route: '/labs/consumables' },
        { icon: 'build', label: 'Maintenance', route: '/labs/maintenance' },
      ],
    },
    {
      icon: 'payments',
      label: 'Finance',
      route: '/finance',
      children: [
        { icon: 'receipt_long', label: 'Fee Structures', route: '/finance/fee-structures' },
        { icon: 'payment', label: 'Payments', route: '/finance/payments' },
        { icon: 'redeem', label: 'Scholarships', route: '/finance/scholarships' },
        { icon: 'science', label: 'Lab Fees', route: '/finance/lab-fees' },
      ],
    },
    {
      icon: 'fact_check',
      label: 'Attendance',
      route: '/attendance',
      children: [
        { icon: 'checklist', label: 'Lab Attendance', route: '/attendance/lab' },
        { icon: 'notification_important', label: 'Alerts', route: '/attendance/alerts' },
      ],
    },
  ];

  toggleSidebar(): void {
    this.sidebarCollapsed.update((v) => !v);
  }

  toggleGroup(label: string): void {
    this.expandedGroup.update((current) => (current === label ? null : label));
  }

  isGroupExpanded(label: string): boolean {
    return this.expandedGroup() === label;
  }
}
