# CMS Android App

Native Android application for the **College Management System**, providing role-based mobile access for teachers, students, parents, drivers, administrators, lab in-charges, and technicians.

## Tech Stack

| Component | Technology |
|---|---|
| Language | Kotlin 2.1 |
| UI | Jetpack Compose + Material 3 |
| Networking | Retrofit 2 + OkHttp 4 |
| Authentication | AppAuth (OpenID Connect) → Keycloak |
| Architecture | MVVM (ViewModel + StateFlow) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 |

## Project Structure

```
android/
├── app/src/main/java/com/cms/android/
│   ├── CmsApplication.kt          # Application class
│   ├── MainActivity.kt            # Single-activity host
│   ├── auth/
│   │   ├── AuthManager.kt         # AppAuth / Keycloak integration
│   │   └── AuthViewModel.kt       # Auth state management
│   ├── data/
│   │   ├── api/
│   │   │   ├── CmsApiService.kt   # Retrofit API interface
│   │   │   └── RetrofitClient.kt  # HTTP client configuration
│   │   ├── model/
│   │   │   └── UserProfile.kt     # User & role models
│   │   └── repository/
│   │       └── UserRepository.kt  # Data access layer
│   └── ui/
│       ├── theme/                  # Material 3 theming
│       ├── navigation/             # Role-based navigation
│       ├── login/                  # Login screen
│       ├── dashboard/              # Main dashboard
│       ├── teacher/                # Teacher-specific screens
│       ├── student/                # Student-specific screens
│       ├── parent/                 # Parent-specific screens
│       ├── driver/                 # Driver-specific screens
│       └── common/                 # Reusable UI components
└── app/src/main/res/               # Android resources
```

## Supported Roles

| Role | Key Features |
|---|---|
| **Admin** | System dashboard, user management, department management |
| **Faculty / Teacher** | Course management, attendance, schedule, grading |
| **Student** | Grades, schedule, class information, assignments |
| **Parent** | Children overview, grades & attendance tracking, notifications |
| **Driver** | Route management, trip schedule, passenger info |
| **Lab In-Charge** | Lab equipment inventory, scheduling, maintenance |
| **Technician** | Lab maintenance, equipment management |

## Prerequisites

- Android Studio Ladybug (2024.2) or later
- JDK 17+
- Android SDK 35
- Running CMS backend and Keycloak (see root `docker-compose.yml`)

## Getting Started

### 1. Start the Backend Services

From the project root:
```bash
docker-compose up -d
```

This starts PostgreSQL, Keycloak (with the `cms` realm pre-configured), and you can then run the Spring Boot backend.

### 2. Open in Android Studio

1. Open Android Studio
2. Select **Open** → navigate to the `android/` directory
3. Let Gradle sync complete

### 3. Run on Emulator

The app is pre-configured to connect to the backend at `10.0.2.2:8080` (the emulator's alias for the host machine's `localhost`).

1. Create/start an Android emulator (API 26+)
2. Click **Run** ▶️ in Android Studio
3. Sign in with one of the test accounts:

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | Administrator |
| `faculty1` | `faculty123` | Faculty / Teacher |
| `student1` | `student123` | Student |
| `parent1` | `parent123` | Parent |
| `driver1` | `driver123` | Driver |
| `labincharge1` | `lab123` | Lab In-Charge |

### 4. Run on Physical Device

For a physical device on the same network, update `API_BASE_URL` and `KEYCLOAK_URL` in `app/build.gradle.kts` to point to your machine's local IP address.

## Authentication Flow

The app uses the **Authorization Code flow with PKCE** via the AppAuth library:

1. User taps "Sign In" → opens Keycloak login page in a Custom Tab
2. After successful login, Keycloak redirects back to `com.cms.android://oauth2callback`
3. AppAuth exchanges the authorization code for access + refresh tokens
4. The access token is attached to all API requests via an OkHttp interceptor
5. The app fetches the user profile from `/api/v1/me` to determine the user's role
6. Role-based dashboard is displayed

## Keycloak Configuration

A `cms-mobile` client has been added to the Keycloak realm configuration (`infrastructure/keycloak/cms-realm.json`):

- **Client ID**: `cms-mobile`
- **Type**: Public client (no client secret)
- **Flow**: Authorization Code with PKCE (S256)
- **Redirect URI**: `com.cms.android://oauth2callback`

## Build Commands

```bash
# Build debug APK
cd android && ./gradlew assembleDebug

# Build release APK
cd android && ./gradlew assembleRelease

# Run lint checks
cd android && ./gradlew lint
```
