import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/dashboard/dashboard.component').then(
        (m) => m.DashboardComponent
      ),
  },
  // ── Admin ──
  {
    path: 'admin/departments',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/department-list/department-list.component').then(
        (m) => m.DepartmentListComponent
      ),
  },
  {
    path: 'admin/programs',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/program-list/program-list.component').then(
        (m) => m.ProgramListComponent
      ),
  },
  {
    path: 'admin/courses',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/course-list/course-list.component').then(
        (m) => m.CourseListComponent
      ),
  },
  {
    path: 'admin/academic-years',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/academic-year-list/academic-year-list.component').then(
        (m) => m.AcademicYearListComponent
      ),
  },
  {
    path: 'admin/semesters',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/semester-list/semester-list.component').then(
        (m) => m.SemesterListComponent
      ),
  },
  {
    path: 'admin/calendar-events',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/admin/calendar-event-list/calendar-event-list.component').then(
        (m) => m.CalendarEventListComponent
      ),
  },
  // ── Faculty ──
  {
    path: 'faculty/profiles',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/faculty/faculty-profile-list/faculty-profile-list.component').then(
        (m) => m.FacultyProfileListComponent
      ),
  },
  {
    path: 'faculty/lab-expertise',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/faculty/faculty-lab-expertise-list/faculty-lab-expertise-list.component'
      ).then((m) => m.FacultyLabExpertiseListComponent),
  },
  {
    path: 'faculty/lab-assignments',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/faculty/faculty-lab-assignment-list/faculty-lab-assignment-list.component'
      ).then((m) => m.FacultyLabAssignmentListComponent),
  },
  // ── Students ──
  {
    path: 'students/profiles',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/students/student-profile-list/student-profile-list.component').then(
        (m) => m.StudentProfileListComponent
      ),
  },
  {
    path: 'students/enrollments',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/students/student-enrollment-list/student-enrollment-list.component'
      ).then((m) => m.StudentEnrollmentListComponent),
  },
  {
    path: 'students/gpa-records',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/students/gpa-record-list/gpa-record-list.component').then(
        (m) => m.GpaRecordListComponent
      ),
  },
  {
    path: 'students/admissions',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/students/admission-list/admission-list.component').then(
        (m) => m.AdmissionListComponent
      ),
  },
  // ── Labs ──
  {
    path: 'labs/list',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/lab-list/lab-list.component').then((m) => m.LabListComponent),
  },
  {
    path: 'labs/types',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/lab-type-list/lab-type-list.component').then(
        (m) => m.LabTypeListComponent
      ),
  },
  {
    path: 'labs/equipment',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/equipment-list/equipment-list.component').then(
        (m) => m.EquipmentListComponent
      ),
  },
  {
    path: 'labs/timetable',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/lab-timetable-list/lab-timetable-list.component').then(
        (m) => m.LabTimetableListComponent
      ),
  },
  {
    path: 'labs/consumables',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/consumable-list/consumable-list.component').then(
        (m) => m.ConsumableListComponent
      ),
  },
  {
    path: 'labs/maintenance',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/labs/maintenance-list/maintenance-list.component').then(
        (m) => m.MaintenanceListComponent
      ),
  },
  // ── Finance ──
  {
    path: 'finance/fee-structures',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/finance/fee-structure-list/fee-structure-list.component').then(
        (m) => m.FeeStructureListComponent
      ),
  },
  {
    path: 'finance/payments',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/finance/payment-list/payment-list.component').then(
        (m) => m.PaymentListComponent
      ),
  },
  {
    path: 'finance/scholarships',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/finance/scholarship-list/scholarship-list.component').then(
        (m) => m.ScholarshipListComponent
      ),
  },
  {
    path: 'finance/lab-fees',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/finance/lab-fee-list/lab-fee-list.component').then(
        (m) => m.LabFeeListComponent
      ),
  },
  // ── Attendance ──
  {
    path: 'attendance/lab',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/attendance/lab-attendance-list/lab-attendance-list.component'
      ).then((m) => m.LabAttendanceListComponent),
  },
  {
    path: 'attendance/alerts',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/attendance/attendance-alert-list/attendance-alert-list.component'
      ).then((m) => m.AttendanceAlertListComponent),
  },
  // ── Transport ──
  {
    path: 'transport/vehicles',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/transport/vehicle-list/vehicle-list.component').then(
        (m) => m.VehicleListComponent
      ),
  },
  {
    path: 'transport/routes',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/transport/route-list/route-list.component').then(
        (m) => m.RouteListComponent
      ),
  },
  // ── Infrastructure ──
  {
    path: 'infrastructure/buildings',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/infrastructure/building-list/building-list.component').then(
        (m) => m.BuildingListComponent
      ),
  },
  {
    path: 'infrastructure/facilities',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/infrastructure/facility-list/facility-list.component').then(
        (m) => m.FacilityListComponent
      ),
  },
  {
    path: 'infrastructure/maintenance-requests',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/infrastructure/maintenance-request-list/maintenance-request-list.component'
      ).then((m) => m.MaintenanceRequestListComponent),
  },
  // ── Exams ──
  {
    path: 'exams/schedules',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/exams/exam-schedule-list/exam-schedule-list.component').then(
        (m) => m.ExamScheduleListComponent
      ),
  },
  {
    path: 'exams/grade-scales',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/exams/grade-scale-list/grade-scale-list.component').then(
        (m) => m.GradeScaleListComponent
      ),
  },
  // ── Hostel ──
  {
    path: 'hostel/list',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/hostel/hostel-list/hostel-list.component').then(
        (m) => m.HostelListComponent
      ),
  },
  {
    path: 'hostel/mess-menus',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/hostel/mess-menu-list/mess-menu-list.component').then(
        (m) => m.MessMenuListComponent
      ),
  },
  // ── Placement ──
  {
    path: 'placement/companies',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/placement/company-list/company-list.component').then(
        (m) => m.CompanyListComponent
      ),
  },
  {
    path: 'placement/drives',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/placement/drive-list/drive-list.component').then(
        (m) => m.DriveListComponent
      ),
  },
  {
    path: 'placement/internships',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/placement/internship-list/internship-list.component').then(
        (m) => m.InternshipListComponent
      ),
  },
  // ── Library ──
  {
    path: 'library/books',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/library/book-list/book-list.component').then(
        (m) => m.BookListComponent
      ),
  },
  {
    path: 'library/members',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/library/member-list/member-list.component').then(
        (m) => m.MemberListComponent
      ),
  },
  // ── Communication ──
  {
    path: 'communication/notifications',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/communication/notification-list/notification-list.component'
      ).then((m) => m.NotificationListComponent),
  },
  {
    path: 'communication/announcements',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/communication/announcement-list/announcement-list.component'
      ).then((m) => m.AnnouncementListComponent),
  },
  // ── Feedback ──
  {
    path: 'feedback/forms',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/feedback/feedback-form-list/feedback-form-list.component').then(
        (m) => m.FeedbackFormListComponent
      ),
  },
  // ── Grievance ──
  {
    path: 'grievance/list',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/grievance/grievance-list/grievance-list.component').then(
        (m) => m.GrievanceListComponent
      ),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
