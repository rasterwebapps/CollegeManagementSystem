package com.cms.android.ui.parent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cms.android.data.model.UserProfile
import com.cms.android.ui.common.ComingSoonCard
import com.cms.android.ui.common.SectionHeader
import com.cms.android.ui.common.SummaryCard

@Composable
fun ParentHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Parent Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SummaryCard(
                title = "Children",
                value = "2",
                icon = Icons.Default.ChildCare,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            SummaryCard(
                title = "Avg GPA",
                value = "3.5",
                icon = Icons.Default.Grade,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Notifications")
        ComingSoonCard("Fee Payment Reminders")
        Spacer(modifier = Modifier.height(8.dp))
        ComingSoonCard("Parent-Teacher Meetings")
    }
}

@Composable
fun ParentChildrenContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "My Children",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        ChildCard("Alex Johnson", "Grade 10 - Section A", "GPA: 3.8", "92% Attendance")
        Spacer(modifier = Modifier.height(8.dp))
        ChildCard("Emma Johnson", "Grade 7 - Section B", "GPA: 3.2", "88% Attendance")
    }
}

@Composable
private fun ChildCard(
    name: String,
    grade: String,
    gpa: String,
    attendance: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = name,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = grade,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row {
                    Text(
                        text = gpa,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = attendance,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}
