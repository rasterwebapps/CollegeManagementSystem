# Manual Test Cases — CMS Android App

## TC-ANDROID-001: Login via Keycloak

**Preconditions:** Backend services running (Docker Compose up), Keycloak accessible, Android emulator running.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Launch the CMS app on an Android emulator | Login screen is displayed with the CMS logo and "Sign In with Keycloak" button |
| 2 | Tap "Sign In with Keycloak" | A Custom Tab / browser opens showing the Keycloak login page for the `cms` realm |
| 3 | Enter `student1` / `student123` and tap Login | Redirected back to the app; loading indicator shown briefly |
| 4 | Observe the dashboard | Student dashboard is displayed with correct username in the top bar and "Student" role label |

## TC-ANDROID-002: Role-Based Dashboard — Teacher

**Preconditions:** Same as TC-ANDROID-001.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Launch the app and sign in as `faculty1` / `faculty123` | Teacher dashboard is displayed |
| 2 | Verify top bar | Shows "Welcome, faculty1" and role "Faculty / Teacher" |
| 3 | Verify bottom navigation | Shows Home, Courses, Attendance, and Profile tabs |
| 4 | Tap "Courses" tab | Teacher Courses screen shows a list of sample courses |
| 5 | Tap "Attendance" tab | Attendance screen shows "Coming soon" placeholders |

## TC-ANDROID-003: Role-Based Dashboard — Student

**Preconditions:** Same as TC-ANDROID-001.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Sign in as `student1` / `student123` | Student dashboard with summary cards (Enrolled, GPA) |
| 2 | Verify bottom navigation | Shows Home, Grades, Schedule, Profile tabs |
| 3 | Tap "Grades" tab | Shows list of courses with grade letters and progress bars |
| 4 | Tap "Profile" tab | Shows Name, Email, Role, and User ID |

## TC-ANDROID-004: Role-Based Dashboard — Parent

**Preconditions:** Same as TC-ANDROID-001. A user with `ROLE_PARENT` exists in Keycloak.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Sign in as `parent1` / `parent123` | Parent dashboard with summary cards (Children, Avg GPA) |
| 2 | Tap "Children" tab | Shows child cards with name, grade, GPA, and attendance |

## TC-ANDROID-005: Role-Based Dashboard — Driver

**Preconditions:** Same as TC-ANDROID-001. A user with `ROLE_DRIVER` exists in Keycloak.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Sign in as `driver1` / `driver123` | Driver dashboard with summary cards (My Routes, Passengers) |
| 2 | Verify "Today's Trips" section | Shows trip cards with route, time, and status |
| 3 | Tap "Routes" tab | Shows route cards with stops, distance, and passenger count |

## TC-ANDROID-006: Role-Based Dashboard — Admin

**Preconditions:** Same as TC-ANDROID-001.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Sign in as `admin` / `admin123` | Admin dashboard with "Coming soon" cards for User Management, Department Management, System Reports |
| 2 | Verify bottom navigation | Shows Dashboard and Profile tabs |

## TC-ANDROID-007: Logout

**Preconditions:** User is currently logged in.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Tap the logout icon in the top-right corner of the top bar | Browser opens briefly for Keycloak logout |
| 2 | Observe the app | Returns to the Login screen |
| 3 | Tap "Sign In with Keycloak" again | Keycloak login page is shown (user is not auto-logged in) |

## TC-ANDROID-008: Network Error Handling

**Preconditions:** Backend services are NOT running.

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Launch the app and attempt to sign in | An error snackbar message is displayed |
| 2 | Verify login button state | Login button remains enabled for retry |
