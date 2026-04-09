import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface StudentProfile {
  id: number;
  keycloakId: string;
  rollNumber: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  programId: number;
  programName: string;
  admissionYear: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface StudentEnrollment {
  id: number;
  studentId: number;
  studentName: string;
  courseId: number;
  courseName: string;
  semesterId: number;
  semesterName: string;
  enrollmentDate: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface GpaRecord {
  id: number;
  studentId: number;
  studentName: string;
  semesterId: number;
  semesterName: string;
  sgpa: number;
  cgpa: number;
  totalCredits: number;
  createdAt: string;
  updatedAt: string;
}

export interface Admission {
  id: number;
  applicationNumber: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  programId: number;
  programName: string;
  status: string;
  applicationDate: string;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class StudentService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  readonly profiles = signal<StudentProfile[]>([]);
  readonly enrollments = signal<StudentEnrollment[]>([]);
  readonly gpaRecords = signal<GpaRecord[]>([]);
  readonly admissions = signal<Admission[]>([]);

  loadProfiles() {
    this.http
      .get<StudentProfile[]>(`${this.baseUrl}/student-profiles`)
      .subscribe((data) => this.profiles.set(data));
  }

  createProfile(p: Partial<StudentProfile>) {
    return this.http.post<StudentProfile>(`${this.baseUrl}/student-profiles`, p);
  }

  updateProfile(id: number, p: Partial<StudentProfile>) {
    return this.http.put<StudentProfile>(`${this.baseUrl}/student-profiles/${id}`, p);
  }

  deleteProfile(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/student-profiles/${id}`);
  }

  loadEnrollments() {
    this.http
      .get<StudentEnrollment[]>(`${this.baseUrl}/student-enrollments`)
      .subscribe((data) => this.enrollments.set(data));
  }

  deleteEnrollment(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/student-enrollments/${id}`);
  }

  loadGpaRecords() {
    this.http
      .get<GpaRecord[]>(`${this.baseUrl}/gpa-records`)
      .subscribe((data) => this.gpaRecords.set(data));
  }

  deleteGpaRecord(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/gpa-records/${id}`);
  }

  loadAdmissions() {
    this.http
      .get<Admission[]>(`${this.baseUrl}/admissions`)
      .subscribe((data) => this.admissions.set(data));
  }

  deleteAdmission(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/admissions/${id}`);
  }
}
