package com.cms.android.ui.teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cms.android.data.model.UserProfile
import com.cms.android.ui.common.ComingSoonCard
import com.cms.android.ui.common.SectionHeader
import com.cms.android.ui.common.SummaryCard

@Composable
fun TeacherHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Teacher Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SummaryCard(
                title = "My Courses",
                value = "5",
                icon = Icons.Default.Class,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            SummaryCard(
                title = "Students",
                value = "142",
                icon = Icons.Default.Group,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Today's Schedule")
        ScheduleItem("9:00 AM", "Data Structures - Room 201")
        ScheduleItem("11:00 AM", "Algorithms - Room 305")
        ScheduleItem("2:00 PM", "Database Systems - Lab 102")

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Pending Tasks")
        ComingSoonCard("Assignment Grading")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Attendance Reports")
    }
}

@Composable
fun TeacherCoursesContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "My Courses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        CourseCard("CS301 - Data Structures", "MWF 9:00-10:00 AM", "45 students")
        Spacer(modifier = Modifier.height(8.dp))
        CourseCard("CS401 - Algorithms", "TTh 11:00-12:30 PM", "38 students")
        Spacer(modifier = Modifier.height(8.dp))
        CourseCard("CS501 - Database Systems", "MWF 2:00-3:00 PM", "32 students")
        Spacer(modifier = Modifier.height(8.dp))
        CourseCard("CS201 - Programming Fundamentals", "TTh 9:00-10:30 AM", "50 students")
        Spacer(modifier = Modifier.height(8.dp))
        CourseCard("CS601 - Software Engineering", "MW 4:00-5:30 PM", "27 students")
    }
}

@Composable
fun TeacherAttendanceContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Attendance",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ComingSoonCard("Mark Attendance")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("View Attendance Reports")
    }
}

@Composable
private fun ScheduleItem(time: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                text = time,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun CourseCard(name: String, schedule: String, enrollment: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = schedule,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = enrollment,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
