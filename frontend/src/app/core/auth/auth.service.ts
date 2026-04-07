import { Injectable, signal, computed } from '@angular/core';
import Keycloak from 'keycloak-js';
import { environment } from '../../../environments/environment';

export interface UserProfile {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private keycloak: Keycloak;
  private readonly _authenticated = signal(false);
  private readonly _userProfile = signal<UserProfile | null>(null);
  private readonly _roles = signal<string[]>([]);

  readonly authenticated = this._authenticated.asReadonly();
  readonly userProfile = this._userProfile.asReadonly();
  readonly roles = this._roles.asReadonly();

  readonly isAdmin = computed(() => this._roles().includes('ROLE_ADMIN'));
  readonly isFaculty = computed(() => this._roles().includes('ROLE_FACULTY'));
  readonly isStudent = computed(() => this._roles().includes('ROLE_STUDENT'));
  readonly isLabIncharge = computed(() => this._roles().includes('ROLE_LAB_INCHARGE'));
  readonly isTechnician = computed(() => this._roles().includes('ROLE_TECHNICIAN'));

  constructor() {
    this.keycloak = new Keycloak({
      url: environment.keycloak.url,
      realm: environment.keycloak.realm,
      clientId: environment.keycloak.clientId,
    });
  }

  async init(): Promise<boolean> {
    const authenticated = await this.keycloak.init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri:
        window.location.origin + '/assets/silent-check-sso.html',
      checkLoginIframe: false,
    });

    this._authenticated.set(authenticated);

    if (authenticated) {
      await this.loadUserProfile();
    }

    return authenticated;
  }

  async login(): Promise<void> {
    await this.keycloak.login();
  }

  async logout(): Promise<void> {
    await this.keycloak.logout({ redirectUri: window.location.origin });
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  async updateToken(minValidity: number = 30): Promise<string> {
    await this.keycloak.updateToken(minValidity);
    return this.keycloak.token!;
  }

  private async loadUserProfile(): Promise<void> {
    const profile = await this.keycloak.loadUserProfile();
    const realmRoles = this.keycloak.realmAccess?.roles ?? [];

    this._roles.set(realmRoles.filter((r) => r.startsWith('ROLE_')));
    this._userProfile.set({
      username: profile.username ?? '',
      email: profile.email ?? '',
      firstName: profile.firstName ?? '',
      lastName: profile.lastName ?? '',
      roles: this._roles(),
    });
  }
}
