import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_VEHICLES,
  GET_TRANSPORT_ROUTES,
  GET_TRANSPORT_PASSES,
} from '../../../core/graphql/operations';

export interface Vehicle {
  id: number;
  type: string;
  registrationNumber: string;
  capacity: number;
  driverName: string;
  driverPhone: string;
  insuranceExpiry: string;
  status: string;
}

export interface TransportRoute {
  id: number;
  routeName: string;
  stops: string;
  startTime: string;
  endTime: string;
  distanceKm: number;
  status: string;
}

export interface TransportPass {
  id: number;
  passType: string;
  validFrom: string;
  validTo: string;
  fee: number;
  status: string;
}

@Injectable({ providedIn: 'root' })
export class TransportService {
  private readonly apollo = inject(Apollo);

  readonly vehicles = signal<Vehicle[]>([]);
  readonly routes = signal<TransportRoute[]>([]);
  readonly passes = signal<TransportPass[]>([]);

  loadVehicles() {
    this.apollo
      .watchQuery<{ vehicles: Vehicle[] }>({ query: GET_VEHICLES })
      .valueChanges.subscribe(({ data }) => this.vehicles.set(data?.vehicles ?? []));
  }

  loadRoutes() {
    this.apollo
      .watchQuery<{ transportRoutes: TransportRoute[] }>({ query: GET_TRANSPORT_ROUTES })
      .valueChanges.subscribe(({ data }) => this.routes.set(data?.transportRoutes ?? []));
  }

  loadPasses() {
    this.apollo
      .watchQuery<{ transportPasses: TransportPass[] }>({ query: GET_TRANSPORT_PASSES })
      .valueChanges.subscribe(({ data }) => this.passes.set(data?.transportPasses ?? []));
  }
}
