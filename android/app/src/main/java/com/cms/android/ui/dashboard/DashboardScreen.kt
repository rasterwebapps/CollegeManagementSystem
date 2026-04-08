package com.cms.android.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cms.android.R
import com.cms.android.data.model.CmsRole
import com.cms.android.data.model.UserProfile
import com.cms.android.ui.common.ComingSoonCard
import com.cms.android.ui.driver.DriverHomeContent
import com.cms.android.ui.driver.DriverRoutesContent
import com.cms.android.ui.navigation.Routes
import com.cms.android.ui.navigation.getBottomNavItems
import com.cms.android.ui.parent.ParentChildrenContent
import com.cms.android.ui.parent.ParentHomeContent
import com.cms.android.ui.student.StudentGradesContent
import com.cms.android.ui.student.StudentHomeContent
import com.cms.android.ui.student.StudentScheduleContent
import com.cms.android.ui.teacher.TeacherAttendanceContent
import com.cms.android.ui.teacher.TeacherCoursesContent
import com.cms.android.ui.teacher.TeacherHomeContent

/**
 * Main dashboard screen after login. Shows role-based content with
 * a bottom navigation bar customized per CMS role.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    profile: UserProfile,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navItems = getBottomNavItems(profile.primaryRole)
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.welcome_message, profile.displayName),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = profile.primaryRole.displayName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* notifications */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = stringResource(R.string.logout_button),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                    )
                }
            }
        },
    ) { padding ->
        val currentRoute = navItems.getOrNull(selectedIndex)?.route ?: Routes.DASHBOARD

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            when (currentRoute) {
                // Teacher / Faculty
                Routes.TEACHER_HOME -> TeacherHomeContent(profile)
                Routes.TEACHER_COURSES -> TeacherCoursesContent()
                Routes.TEACHER_ATTENDANCE -> TeacherAttendanceContent()

                // Student
                Routes.STUDENT_HOME -> StudentHomeContent(profile)
                Routes.STUDENT_GRADES -> StudentGradesContent()
                Routes.STUDENT_SCHEDULE -> StudentScheduleContent()

                // Parent
                Routes.PARENT_HOME -> ParentHomeContent(profile)
                Routes.PARENT_CHILDREN -> ParentChildrenContent()

                // Driver
                Routes.DRIVER_HOME -> DriverHomeContent(profile)
                Routes.DRIVER_ROUTES -> DriverRoutesContent()

                // Admin
                Routes.ADMIN_HOME -> AdminHomeContent(profile)

                // Lab
                Routes.LAB_HOME -> LabHomeContent(profile)

                // Profile
                Routes.PROFILE -> ProfileContent(profile)

                // Fallback
                else -> DefaultDashboardContent(profile)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AdminHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Admin Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ComingSoonCard("User Management")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Department Management")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("System Reports")
    }
}

@Composable
fun LabHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Lab Management",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ComingSoonCard("Lab Equipment Inventory")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Lab Schedule")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Maintenance Requests")
    }
}

@Composable
fun ProfileContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProfileRow("Name", profile.displayName)
        ProfileRow("Email", profile.email ?: "Not available")
        ProfileRow("Role", profile.primaryRole.displayName)
        ProfileRow("User ID", profile.sub)
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun DefaultDashboardContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to the College Management System.",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
