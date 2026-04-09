import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface FeeStructure {
  id: number;
  programId: number;
  programName: string;
  academicYearId: number;
  academicYearName: string;
  feeType: string;
  amount: number;
  dueDate: string;
  createdAt: string;
  updatedAt: string;
}

export interface Payment {
  id: number;
  studentId: number;
  studentName: string;
  feeStructureId: number;
  amount: number;
  paymentDate: string;
  paymentMethod: string;
  transactionId: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface Scholarship {
  id: number;
  name: string;
  description: string;
  amount: number;
  studentId: number;
  studentName: string;
  academicYearId: number;
  academicYearName: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface LabFee {
  id: number;
  labId: number;
  labName: string;
  semesterId: number;
  semesterName: string;
  amount: number;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class FinanceService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  readonly feeStructures = signal<FeeStructure[]>([]);
  readonly payments = signal<Payment[]>([]);
  readonly scholarships = signal<Scholarship[]>([]);
  readonly labFees = signal<LabFee[]>([]);

  loadFeeStructures() {
    this.http.get<FeeStructure[]>(`${this.baseUrl}/fee-structures`).subscribe((data) => this.feeStructures.set(data));
  }

  deleteFeeStructure(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/fee-structures/${id}`);
  }

  loadPayments() {
    this.http.get<Payment[]>(`${this.baseUrl}/payments`).subscribe((data) => this.payments.set(data));
  }

  deletePayment(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/payments/${id}`);
  }

  loadScholarships() {
    this.http.get<Scholarship[]>(`${this.baseUrl}/scholarships`).subscribe((data) => this.scholarships.set(data));
  }

  deleteScholarship(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/scholarships/${id}`);
  }

  loadLabFees() {
    this.http.get<LabFee[]>(`${this.baseUrl}/lab-fees`).subscribe((data) => this.labFees.set(data));
  }

  deleteLabFee(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/lab-fees/${id}`);
  }
}
