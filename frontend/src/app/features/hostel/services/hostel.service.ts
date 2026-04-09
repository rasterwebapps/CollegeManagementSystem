import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { GET_HOSTELS, GET_HOSTEL_ROOMS, GET_MESS_MENUS } from '../../../core/graphql/operations';

export interface Hostel {
  id: number;
  name: string;
  code: string;
  hostelType: string;
  totalCapacity: number;
  occupiedBeds: number;
  contactPhone: string;
  contactEmail: string;
  status: string;
}

export interface HostelRoom {
  id: number;
  roomNumber: string;
  floor: number;
  roomType: string;
  capacity: number;
  currentOccupancy: number;
  monthlyRent: number;
  status: string;
}

export interface MessMenu {
  id: number;
  dayOfWeek: string;
  mealType: string;
  items: string;
}

@Injectable({ providedIn: 'root' })
export class HostelService {
  private readonly apollo = inject(Apollo);

  readonly hostels = signal<Hostel[]>([]);
  readonly rooms = signal<HostelRoom[]>([]);
  readonly messMenus = signal<MessMenu[]>([]);

  loadHostels() {
    this.apollo
      .watchQuery<{ hostels: Hostel[] }>({ query: GET_HOSTELS })
      .valueChanges.subscribe(({ data }) => this.hostels.set((data?.hostels ?? []) as Hostel[]));
  }

  loadRooms(hostelId: number) {
    this.apollo
      .watchQuery<{ hostelRooms: HostelRoom[] }>({
        query: GET_HOSTEL_ROOMS,
        variables: { hostelId },
      })
      .valueChanges.subscribe(({ data }) => this.rooms.set((data?.hostelRooms ?? []) as HostelRoom[]));
  }

  loadMessMenus(hostelId: number) {
    this.apollo
      .watchQuery<{ messMenus: MessMenu[] }>({
        query: GET_MESS_MENUS,
        variables: { hostelId },
      })
      .valueChanges.subscribe(({ data }) => this.messMenus.set(data?.messMenus ?? []));
  }
}
