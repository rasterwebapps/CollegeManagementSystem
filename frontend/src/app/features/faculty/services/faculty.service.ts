import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface FacultyProfile {
  id: number;
  keycloakId: string;
  employeeCode: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  departmentId: number;
  departmentName: string;
  designation: string;
  joiningDate: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface FacultyLabExpertise {
  id: number;
  facultyId: number;
  facultyName: string;
  labTypeId: number;
  labTypeName: string;
  proficiencyLevel: string;
  certifiedDate: string;
  createdAt: string;
  updatedAt: string;
}

export interface FacultyLabAssignment {
  id: number;
  facultyId: number;
  facultyName: string;
  labId: number;
  labName: string;
  semesterId: number;
  semesterName: string;
  assignedDate: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class FacultyService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  readonly profiles = signal<FacultyProfile[]>([]);
  readonly labExpertise = signal<FacultyLabExpertise[]>([]);
  readonly labAssignments = signal<FacultyLabAssignment[]>([]);

  loadProfiles() {
    this.http
      .get<FacultyProfile[]>(`${this.baseUrl}/faculty-profiles`)
      .subscribe((data) => this.profiles.set(data));
  }

  deleteProfile(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/faculty-profiles/${id}`);
  }

  loadLabExpertise() {
    this.http
      .get<FacultyLabExpertise[]>(`${this.baseUrl}/faculty-lab-expertise`)
      .subscribe((data) => this.labExpertise.set(data));
  }

  deleteLabExpertise(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/faculty-lab-expertise/${id}`);
  }

  loadLabAssignments() {
    this.http
      .get<FacultyLabAssignment[]>(`${this.baseUrl}/faculty-lab-assignments`)
      .subscribe((data) => this.labAssignments.set(data));
  }

  deleteLabAssignment(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/faculty-lab-assignments/${id}`);
  }
}
