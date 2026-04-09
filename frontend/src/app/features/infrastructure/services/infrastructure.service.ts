import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_BUILDINGS,
  GET_CAMPUS_FACILITIES,
  GET_MAINTENANCE_REQUESTS,
} from '../../../core/graphql/operations';

export interface Building {
  id: number;
  name: string;
  code: string;
  floors: number;
  address: string;
  status: string;
}

export interface CampusFacility {
  id: number;
  name: string;
  type: string;
  capacity: number;
  location: string;
  description: string;
  status: string;
}

export interface MaintenanceRequest {
  id: number;
  requesterName: string;
  location: string;
  description: string;
  priority: string;
  status: string;
  assignedTo: string;
}

@Injectable({ providedIn: 'root' })
export class InfrastructureService {
  private readonly apollo = inject(Apollo);

  readonly buildings = signal<Building[]>([]);
  readonly facilities = signal<CampusFacility[]>([]);
  readonly maintenanceRequests = signal<MaintenanceRequest[]>([]);

  loadBuildings() {
    this.apollo
      .watchQuery<{ buildings: Building[] }>({ query: GET_BUILDINGS })
      .valueChanges.subscribe(({ data }) => this.buildings.set(data?.buildings ?? []));
  }

  loadFacilities() {
    this.apollo
      .watchQuery<{ campusFacilities: CampusFacility[] }>({ query: GET_CAMPUS_FACILITIES })
      .valueChanges.subscribe(({ data }) => this.facilities.set(data?.campusFacilities ?? []));
  }

  loadMaintenanceRequests() {
    this.apollo
      .watchQuery<{ maintenanceRequests: MaintenanceRequest[] }>({
        query: GET_MAINTENANCE_REQUESTS,
      })
      .valueChanges.subscribe(({ data }) =>
        this.maintenanceRequests.set(data?.maintenanceRequests ?? [])
      );
  }
}
