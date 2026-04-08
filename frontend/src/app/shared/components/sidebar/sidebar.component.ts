import { Component, inject, signal, computed, viewChild } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {
  MatSidenav,
  MatSidenavContainer,
  MatSidenavContent,
} from '@angular/material/sidenav';
import { MatNavList, MatListItem } from '@angular/material/list';
import {
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle,
} from '@angular/material/expansion';
import { MatIcon } from '@angular/material/icon';
import { AuthService } from '../../../core/auth/auth.service';

export interface NavChild {
  label: string;
  route: string;
}

export interface NavItem {
  label: string;
  icon: string;
  route: string;
  children?: NavChild[];
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    MatSidenav,
    MatSidenavContainer,
    MatSidenavContent,
    MatNavList,
    MatListItem,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatIcon,
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  protected readonly authService = inject(AuthService);
  protected readonly sidenav = viewChild<MatSidenav>('sidenav');

  readonly collapsed = signal(false);

  protected readonly navItems = computed<NavItem[]>(() => {
    const items: NavItem[] = [
      { label: 'Dashboard', icon: 'dashboard', route: '/' },
    ];

    if (this.authService.isAdmin()) {
      items.push({
        label: 'Administration',
        icon: 'admin_panel_settings',
        route: '/administration',
        children: [
          { label: 'Departments', route: '/administration/departments' },
          { label: 'Programs', route: '/administration/programs' },
          { label: 'Courses', route: '/administration/courses' },
          { label: 'Academic Years', route: '/administration/academic-years' },
          { label: 'Calendar', route: '/administration/calendar' },
        ],
      });
    }

    if (this.authService.isAdmin() || this.authService.isLabIncharge()) {
      items.push({
        label: 'Labs',
        icon: 'science',
        route: '/labs',
        children: [
          { label: 'Lab Types', route: '/labs/lab-types' },
          { label: 'Lab Registry', route: '/labs/registry' },
        ],
      });
    }

    if (this.authService.isAdmin() || this.authService.isFaculty()) {
      items.push({
        label: 'Curriculum',
        icon: 'menu_book',
        route: '/curriculum',
        children: [
          { label: 'Course Outcomes', route: '/curriculum/course-outcomes' },
          { label: 'Experiments', route: '/curriculum/experiments' },
          { label: 'Lab Mapping', route: '/curriculum/lab-mapping' },
        ],
      });

      items.push({
        label: 'Faculty',
        icon: 'people',
        route: '/faculty',
        children: [
          { label: 'Directory', route: '/faculty/directory' },
          { label: 'Lab Expertise', route: '/faculty/lab-expertise' },
          { label: 'Lab Assignments', route: '/faculty/lab-assignments' },
        ],
      });
    }

    return items;
  });

  toggle(): void {
    this.sidenav()?.toggle();
  }

  closeMobile(): void {
    const nav = this.sidenav();
    if (nav && nav.mode === 'over') {
      nav.close();
    }
  }
}
