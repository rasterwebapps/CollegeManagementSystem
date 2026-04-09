import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface Lab {
  id: number;
  name: string;
  code: string;
  labTypeId: number;
  labTypeName: string;
  departmentId: number;
  departmentName: string;
  location: string;
  capacity: number;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface LabType {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface Equipment {
  id: number;
  name: string;
  serialNumber: string;
  labId: number;
  labName: string;
  status: string;
  purchaseDate: string;
  warrantyEndDate: string;
  cost: number;
  createdAt: string;
  updatedAt: string;
}

export interface LabTimetable {
  id: number;
  labId: number;
  labName: string;
  courseId: number;
  courseName: string;
  semesterId: number;
  semesterName: string;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  createdAt: string;
  updatedAt: string;
}

export interface ConsumableStock {
  id: number;
  name: string;
  labId: number;
  labName: string;
  quantity: number;
  unit: string;
  reorderLevel: number;
  createdAt: string;
  updatedAt: string;
}

export interface MaintenanceSchedule {
  id: number;
  equipmentId: number;
  equipmentName: string;
  scheduledDate: string;
  completedDate: string;
  maintenanceType: string;
  status: string;
  notes: string;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class LabService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  readonly labs = signal<Lab[]>([]);
  readonly labTypes = signal<LabType[]>([]);
  readonly equipment = signal<Equipment[]>([]);
  readonly timetables = signal<LabTimetable[]>([]);
  readonly consumables = signal<ConsumableStock[]>([]);
  readonly maintenanceSchedules = signal<MaintenanceSchedule[]>([]);

  loadLabs() {
    this.http.get<Lab[]>(`${this.baseUrl}/labs`).subscribe((data) => this.labs.set(data));
  }

  deleteLab(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/labs/${id}`);
  }

  loadLabTypes() {
    this.http.get<LabType[]>(`${this.baseUrl}/lab-types`).subscribe((data) => this.labTypes.set(data));
  }

  deleteLabType(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/lab-types/${id}`);
  }

  loadEquipment() {
    this.http.get<Equipment[]>(`${this.baseUrl}/equipments`).subscribe((data) => this.equipment.set(data));
  }

  deleteEquipment(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/equipments/${id}`);
  }

  loadTimetables() {
    this.http.get<LabTimetable[]>(`${this.baseUrl}/lab-timetables`).subscribe((data) => this.timetables.set(data));
  }

  deleteTimetable(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/lab-timetables/${id}`);
  }

  loadConsumables() {
    this.http.get<ConsumableStock[]>(`${this.baseUrl}/consumable-stocks`).subscribe((data) => this.consumables.set(data));
  }

  deleteConsumable(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/consumable-stocks/${id}`);
  }

  loadMaintenanceSchedules() {
    this.http.get<MaintenanceSchedule[]>(`${this.baseUrl}/maintenance-schedules`).subscribe((data) => this.maintenanceSchedules.set(data));
  }

  deleteMaintenanceSchedule(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/maintenance-schedules/${id}`);
  }
}
