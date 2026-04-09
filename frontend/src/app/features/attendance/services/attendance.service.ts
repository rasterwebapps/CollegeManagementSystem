import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface LabAttendance {
  id: number;
  studentId: number;
  studentName: string;
  labTimetableId: number;
  labName: string;
  attendanceDate: string;
  status: string;
  remarks: string;
  createdAt: string;
  updatedAt: string;
}

export interface AttendanceAlert {
  id: number;
  studentId: number;
  studentName: string;
  courseId: number;
  courseName: string;
  alertType: string;
  message: string;
  resolved: boolean;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class AttendanceService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  readonly labAttendances = signal<LabAttendance[]>([]);
  readonly alerts = signal<AttendanceAlert[]>([]);

  loadLabAttendances() {
    this.http.get<LabAttendance[]>(`${this.baseUrl}/lab-attendances`).subscribe((data) => this.labAttendances.set(data));
  }

  deleteLabAttendance(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/lab-attendances/${id}`);
  }

  loadAlerts() {
    this.http.get<AttendanceAlert[]>(`${this.baseUrl}/attendance-alerts`).subscribe((data) => this.alerts.set(data));
  }

  deleteAlert(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/attendance-alerts/${id}`);
  }

  resolveAlert(id: number) {
    return this.http.patch<AttendanceAlert>(`${this.baseUrl}/attendance-alerts/${id}/resolve`, {});
  }
}
