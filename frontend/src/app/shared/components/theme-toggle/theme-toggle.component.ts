import { Component, inject, computed } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { ThemeService } from './theme.service';

@Component({
  selector: 'app-theme-toggle',
  standalone: true,
  imports: [MatIconButton, MatIcon, MatTooltip],
  template: `
    <button
      mat-icon-button
      [matTooltip]="tooltipText()"
      (click)="themeService.toggle()"
      aria-label="Toggle light/dark theme"
    >
      <mat-icon>{{ icon() }}</mat-icon>
    </button>
  `,
})
export class ThemeToggleComponent {
  protected readonly themeService = inject(ThemeService);

  protected readonly icon = computed(() =>
    this.themeService.theme() === 'light' ? 'dark_mode' : 'light_mode'
  );

  protected readonly tooltipText = computed(() =>
    this.themeService.theme() === 'light'
      ? 'Switch to dark mode'
      : 'Switch to light mode'
  );
}
