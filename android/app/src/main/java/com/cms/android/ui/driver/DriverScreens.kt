package com.cms.android.ui.driver

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Route
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
fun DriverHomeContent(profile: UserProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Driver Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SummaryCard(
                title = "My Routes",
                value = "3",
                icon = Icons.Default.Route,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            SummaryCard(
                title = "Passengers",
                value = "87",
                icon = Icons.Default.People,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Today's Trips")
        TripCard("Morning Pickup", "Route A - North Campus", "6:30 AM", "In Progress")
        Spacer(modifier = Modifier.height(8.dp))
        TripCard("Afternoon Drop", "Route A - North Campus", "3:30 PM", "Scheduled")
        Spacer(modifier = Modifier.height(8.dp))
        TripCard("Evening Shuttle", "Route C - Downtown", "6:00 PM", "Scheduled")
    }
}

@Composable
fun DriverRoutesContent() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "My Routes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        RouteCard(
            routeName = "Route A - North Campus",
            stops = "12 stops",
            distance = "18.5 km",
            passengers = "32 passengers",
        )
        Spacer(modifier = Modifier.height(8.dp))
        RouteCard(
            routeName = "Route B - South Campus",
            stops = "8 stops",
            distance = "14.2 km",
            passengers = "28 passengers",
        )
        Spacer(modifier = Modifier.height(8.dp))
        RouteCard(
            routeName = "Route C - Downtown",
            stops = "15 stops",
            distance = "22.0 km",
            passengers = "27 passengers",
        )

        Spacer(modifier = Modifier.height(16.dp))
        ComingSoonCard("GPS Tracking")
    }
}

@Composable
private fun TripCard(title: String, route: String, time: String, status: String) {
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
                imageVector = Icons.Default.DirectionsBus,
                contentDescription = title,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = route,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                text = status,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = if (status == "In Progress") {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
    }
}

@Composable
private fun RouteCard(
    routeName: String,
    stops: String,
    distance: String,
    passengers: String,
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
                imageVector = Icons.Default.LocationOn,
                contentDescription = routeName,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = routeName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "$stops • $distance",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = passengers,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
