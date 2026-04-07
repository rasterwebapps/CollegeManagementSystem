import { Injectable, signal, effect } from '@angular/core';

export type ThemeMode = 'light' | 'dark';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly _theme = signal<ThemeMode>(this.getStoredTheme());

  readonly theme = this._theme.asReadonly();

  constructor() {
    effect(() => {
      const theme = this._theme();
      document.body.setAttribute('data-theme', theme);
      document.documentElement.style.colorScheme = theme;
      localStorage.setItem('cms-theme', theme);
    });
  }

  toggle(): void {
    this._theme.update((current) => (current === 'light' ? 'dark' : 'light'));
  }

  private getStoredTheme(): ThemeMode {
    if (typeof window !== 'undefined') {
      const stored = localStorage.getItem('cms-theme');
      if (stored === 'light' || stored === 'dark') {
        return stored;
      }
      if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
        return 'dark';
      }
    }
    return 'light';
  }
}
