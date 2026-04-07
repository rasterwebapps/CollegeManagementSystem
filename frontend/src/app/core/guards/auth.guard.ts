import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

export const authGuard: CanActivateFn = async () => {
  const authService = inject(AuthService);

  if (authService.authenticated()) {
    return true;
  }

  try {
    await authService.login();
  } catch (error) {
    console.error('Failed to redirect to Keycloak login.', error);
  }
  return false;
};

export function roleGuard(...requiredRoles: string[]): CanActivateFn {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.authenticated()) {
      authService.login();
      return false;
    }

    const userRoles = authService.roles();
    const hasRole = requiredRoles.some((role) => userRoles.includes(role));

    if (!hasRole) {
      router.navigate(['/']);
      return false;
    }

    return true;
  };
}
