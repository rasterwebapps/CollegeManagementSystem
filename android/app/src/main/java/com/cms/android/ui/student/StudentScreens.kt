package com.cms.android.ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
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
fun StudentHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Student Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SummaryCard(
                title = "Enrolled",
                value = "6",
                icon = Icons.Default.AutoStories,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            SummaryCard(
                title = "GPA",
                value = "3.7",
                icon = Icons.Default.Grade,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Today's Classes")
        ClassItem("9:00 AM", "Data Structures", "Prof. Smith - Room 201")
        ClassItem("11:00 AM", "Physics II", "Prof. Johnson - Lab 105")
        ClassItem("2:00 PM", "English Composition", "Prof. Davis - Room 310")

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Upcoming Deadlines")
        ComingSoonCard("Assignment Submissions")
    }
}

@Composable
fun StudentGradesContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "My Grades",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        GradeCard("Data Structures", "A", 0.93f)
        Spacer(modifier = Modifier.height(8.dp))
        GradeCard("Algorithms", "A-", 0.90f)
        Spacer(modifier = Modifier.height(8.dp))
        GradeCard("Physics II", "B+", 0.87f)
        Spacer(modifier = Modifier.height(8.dp))
        GradeCard("English Composition", "A", 0.95f)
        Spacer(modifier = Modifier.height(8.dp))
        GradeCard("Calculus III", "B", 0.83f)
        Spacer(modifier = Modifier.height(8.dp))
        GradeCard("Database Systems", "A-", 0.91f)
    }
}

@Composable
fun StudentScheduleContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Weekly Schedule",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ComingSoonCard("Weekly Timetable View")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Exam Schedule")
    }
}

@Composable
private fun ClassItem(time: String, subject: String, detail: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = subject,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Text(
                text = detail,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun GradeCard(course: String, grade: String, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = course,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = grade,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}
