import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { GET_COMPANIES, GET_PLACEMENT_DRIVES, GET_INTERNSHIPS, GET_PLACEMENT_OFFERS } from '../../../core/graphql/operations';

export interface Company {
  id: number;
  name: string;
  industry: string;
  website: string;
  contactPerson: string;
  tier: string;
  status: string;
}

export interface PlacementDrive {
  id: number;
  company: { id: number; name: string };
  title: string;
  driveDate: string;
  lastDateToApply: string;
  rolesOffered: string;
  packageMin: number;
  packageMax: number;
  registeredCount: number;
  selectedCount: number;
  status: string;
}

export interface Internship {
  id: number;
  role: string;
  startDate: string;
  endDate: string;
  stipend: number;
  mentorName: string;
  status: string;
}

export interface PlacementOffer {
  id: number;
  role: string;
  packageAmount: number;
  joiningDate: string;
  status: string;
}

@Injectable({ providedIn: 'root' })
export class PlacementService {
  private readonly apollo = inject(Apollo);

  readonly companies = signal<Company[]>([]);
  readonly drives = signal<PlacementDrive[]>([]);
  readonly internships = signal<Internship[]>([]);
  readonly offers = signal<PlacementOffer[]>([]);

  loadCompanies() {
    this.apollo
      .watchQuery<{ companies: Company[] }>({ query: GET_COMPANIES })
      .valueChanges.subscribe(({ data }) => this.companies.set((data?.companies ?? []) as Company[]));
  }

  loadDrives(academicYearId?: number) {
    this.apollo
      .watchQuery<{ placementDrives: PlacementDrive[] }>({
        query: GET_PLACEMENT_DRIVES,
        variables: { academicYearId },
      })
      .valueChanges.subscribe(({ data }) => this.drives.set((data?.placementDrives ?? []) as PlacementDrive[]));
  }

  loadInternships() {
    this.apollo
      .watchQuery<{ internships: Internship[] }>({ query: GET_INTERNSHIPS })
      .valueChanges.subscribe(({ data }) => this.internships.set(data?.internships ?? []));
  }

  loadOffers() {
    this.apollo
      .watchQuery<{ placementOffers: PlacementOffer[] }>({ query: GET_PLACEMENT_OFFERS })
      .valueChanges.subscribe(({ data }) => this.offers.set(data?.placementOffers ?? []));
  }
}
