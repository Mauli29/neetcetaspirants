package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProfile
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun AnalyticsScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    onStartRecommendedDrill: (String) -> Unit
) {
    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Performance Analytics",
                onBack = onBack,
                showProfile = true
            )
        },
        containerColor = ZenithBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Overall Readiness Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AIR Calibration Readiness",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface)
                        )
                        Surface(
                            color = ZenithSecondaryFixed,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Top 1% Tier",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithOnSecondaryFixedVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Projected AIR Rank", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("AIR 1,420 - 2,100", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = ZenithPrimary, fontFamily = FontFamily.Monospace))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Govt. MBBS Probability", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("99.4%", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = ZenithSecondary, fontFamily = FontFamily.Monospace))
                        }
                    }

                    LinearProgressIndicator(
                        progress = { 0.92f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = ZenithSecondary,
                        trackColor = ZenithSurfaceContainerHigh,
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            // Weak Areas Identification (AI Mistake Diagnostic)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = TimerWarning, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Weak Areas Detected",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface)
                            )
                        }
                        Text(
                            text = "Score Impact: -48 Marks",
                            style = MaterialTheme.typography.labelSmall.copy(color = StatusUnanswered, fontWeight = FontWeight.Bold)
                        )
                    }

                    listOf(
                        Triple("Rotational Motion & Moment of Inertia", "Physics • 48% Accuracy", "rotational"),
                        Triple("Organic Reaction Mechanisms", "Chemistry • 52% Accuracy", "organic"),
                        Triple("Electrostatics & Gauss Law", "Physics • 55% Accuracy", "electrostatics")
                    ).forEach { (chapter, stat, tag) ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onStartRecommendedDrill(chapter) }
                                .testTag("drill_$tag"),
                            color = ZenithSurfaceSubtle,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = chapter, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    Text(text = stat, style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                }
                                Button(
                                    onClick = { onStartRecommendedDrill(chapter) },
                                    colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("Fix ->", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }

            // Time Management Speedometer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Time Velocity Breakdown",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Physics Avg", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("68s / Q", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = ZenithPrimary))
                            Text("Target: 75s (Optimal)", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontSize = 10.sp))
                        }
                        Column {
                            Text("Chemistry Avg", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("48s / Q", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = ZenithPrimary))
                            Text("Target: 50s (Optimal)", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontSize = 10.sp))
                        }
                        Column {
                            Text("Biology Avg", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("35s / Q", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = ZenithPrimary))
                            Text("Target: 40s (Fast!)", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontSize = 10.sp))
                        }
                    }
                }
            }

            // NCERT Coverage Progress
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "NCERT Line-by-Line Syllabus Coverage",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Class 11th NCERT Units", style = MaterialTheme.typography.bodySmall)
                            Text("82%", fontWeight = FontWeight.Bold, color = ZenithPrimary)
                        }
                        LinearProgressIndicator(
                            progress = { 0.82f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = ZenithPrimary,
                            trackColor = ZenithSurfaceContainerHigh,
                            strokeCap = StrokeCap.Round
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Class 12th NCERT Units", style = MaterialTheme.typography.bodySmall)
                            Text("89%", fontWeight = FontWeight.Bold, color = ZenithSecondary)
                        }
                        LinearProgressIndicator(
                            progress = { 0.89f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = ZenithSecondary,
                            trackColor = ZenithSurfaceContainerHigh,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }
}
