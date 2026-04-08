package com.cms.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Science
import androidx.compose.ui.graphics.vector.ImageVector
import com.cms.android.data.model.CmsRole

/** Navigation routes for the app. */
object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val PROFILE = "profile"

    // Teacher / Faculty
    const val TEACHER_HOME = "teacher_home"
    const val TEACHER_COURSES = "teacher_courses"
    const val TEACHER_ATTENDANCE = "teacher_attendance"

    // Student
    const val STUDENT_HOME = "student_home"
    const val STUDENT_GRADES = "student_grades"
    const val STUDENT_SCHEDULE = "student_schedule"

    // Parent
    const val PARENT_HOME = "parent_home"
    const val PARENT_CHILDREN = "parent_children"

    // Driver
    const val DRIVER_HOME = "driver_home"
    const val DRIVER_ROUTES = "driver_routes"

    // Admin
    const val ADMIN_HOME = "admin_home"

    // Lab
    const val LAB_HOME = "lab_home"
}

/** Represents a bottom navigation item. */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

/** Returns role-specific bottom nav items. */
fun getBottomNavItems(role: CmsRole): List<BottomNavItem> = when (role) {
    CmsRole.ADMIN -> listOf(
        BottomNavItem(Routes.ADMIN_HOME, "Dashboard", Icons.Default.Dashboard),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.AdminPanelSettings),
    )
    CmsRole.FACULTY -> listOf(
        BottomNavItem(Routes.TEACHER_HOME, "Home", Icons.Default.Dashboard),
        BottomNavItem(Routes.TEACHER_COURSES, "Courses", Icons.Default.School),
        BottomNavItem(Routes.TEACHER_ATTENDANCE, "Attendance", Icons.Default.School),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
    CmsRole.STUDENT -> listOf(
        BottomNavItem(Routes.STUDENT_HOME, "Home", Icons.Default.Dashboard),
        BottomNavItem(Routes.STUDENT_GRADES, "Grades", Icons.Default.School),
        BottomNavItem(Routes.STUDENT_SCHEDULE, "Schedule", Icons.Default.Schedule),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
    CmsRole.PARENT -> listOf(
        BottomNavItem(Routes.PARENT_HOME, "Home", Icons.Default.Dashboard),
        BottomNavItem(Routes.PARENT_CHILDREN, "Children", Icons.Default.ChildCare),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
    CmsRole.DRIVER -> listOf(
        BottomNavItem(Routes.DRIVER_HOME, "Home", Icons.Default.Dashboard),
        BottomNavItem(Routes.DRIVER_ROUTES, "Routes", Icons.Default.DirectionsBus),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
    CmsRole.LAB_INCHARGE, CmsRole.TECHNICIAN -> listOf(
        BottomNavItem(Routes.LAB_HOME, "Labs", Icons.Default.Science),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
    CmsRole.UNKNOWN -> listOf(
        BottomNavItem(Routes.DASHBOARD, "Dashboard", Icons.Default.Dashboard),
        BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person),
    )
}
