# Release Workflow — Branch-Based Strategy

This project uses a **branch-based release strategy** instead of a tag-based approach. Releases are triggered automatically when code is pushed to a `release/*` branch.

## How It Works

1. **Create a release branch** from `main` (or your integration branch) with the naming pattern `release/<version>`:

   ```bash
   git checkout main
   git pull origin main
   git checkout -b release/1.0.0
   git push origin release/1.0.0
   ```

2. **The workflow triggers automatically** on push to any `release/**` branch.

3. **The workflow performs the following steps:**
   - **Extracts the version** from the branch name (e.g., `release/1.0.0` → `1.0.0`)
   - **Builds and tests the backend** (Gradle `check` including JaCoCo 95% coverage)
   - **Builds the frontend** (Angular production build)
   - **Creates a GitHub Release** with:
     - Tag: `v<version>` (e.g., `v1.0.0`)
     - Backend JAR artifact
     - Frontend distribution archive (`.tar.gz`)

## Branch Naming Convention

| Pattern | Example | Version Extracted |
|---------|---------|-------------------|
| `release/<major>.<minor>.<patch>` | `release/1.0.0` | `1.0.0` |
| `release/<major>.<minor>.<patch>-<pre>` | `release/2.0.0-rc.1` | `2.0.0-rc.1` |
| `release/<year>.<month>` | `release/2026.04` | `2026.04` |

## Release Artifacts

Each release includes:

| Artifact | Description |
|----------|-------------|
| `cms-backend-<version>.jar` | Spring Boot backend (Java 21) |
| `cms-frontend-<version>.tar.gz` | Angular production build |

## Workflow Diagram

```
push to release/* branch
        │
        ▼
  ┌─────────────┐
  │   Prepare    │ ─── Extract version from branch name
  └─────┬───────┘
        │
   ┌────┴────┐
   ▼         ▼
┌──────┐  ┌──────┐
│Build │  │Build │
│Back- │  │Front-│
│end   │  │end   │
└──┬───┘  └──┬───┘
   │         │
   └────┬────┘
        ▼
  ┌───────────┐
  │  Create   │ ─── GitHub Release + Upload Artifacts
  │  Release  │
  └───────────┘
```

## Hotfix Releases

For hotfix releases, create a release branch from the existing release branch or from a specific commit:

```bash
git checkout release/1.0.0
git checkout -b release/1.0.1
# Apply fixes
git push origin release/1.0.1
```

## Prerequisites

The release workflow requires:

- **Java 21** (Temurin distribution) — for backend build
- **Node.js 20** — for frontend build
- **GitHub Actions** `contents: write` permission — for creating releases

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Build fails on backend | Check JaCoCo coverage (95% minimum). Run `./gradlew check` locally. |
| Build fails on frontend | Run `npm ci && npx ng build` locally to reproduce. |
| Release not created | Ensure the branch name matches `release/**` pattern. |
| Version is wrong | Check the branch name — the version is extracted from everything after `release/`. |
