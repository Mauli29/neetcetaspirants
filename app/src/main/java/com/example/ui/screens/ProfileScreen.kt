package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExamType
import com.example.model.UserProfile
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    onSwitchExam: (ExamType) -> Unit,
    onOpenAdminPanel: () -> Unit,
    onSignOut: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var strictCbtMode by remember { mutableStateOf(true) }
    var showExamDialog by remember { mutableStateOf(false) }

    if (showExamDialog) {
        AlertDialog(
            onDismissRequest = { showExamDialog = false },
            title = { Text("Select Target Competitive Exam", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ExamType.values().forEach { exam ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSwitchExam(exam)
                                    showExamDialog = false
                                }
                                .testTag("select_exam_${exam.name}"),
                            color = if (userProfile.selectedExam == exam) ZenithPrimaryFixed else ZenithSurfaceSubtle,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(exam.displayName, fontWeight = FontWeight.Bold)
                                    Text(exam.category, style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant))
                                }
                                if (userProfile.selectedExam == exam) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = ZenithPrimary)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showExamDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Candidate Profile",
                onBack = onBack,
                showProfile = false
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
            // Student Profile Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(ZenithPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = userProfile.fullName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Icon(Icons.Default.Verified, contentDescription = "Verified", tint = ZenithSecondary, modifier = Modifier.size(16.dp))
                        }

                        Text(
                            text = userProfile.email,
                            style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                        )
                        Text(
                            text = "+91 ${userProfile.mobileNumber} • ${userProfile.stateDomicile.split(" ").first()}",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                        )
                    }
                }
            }

            // Target Exam Track Switcher Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showExamDialog = true }
                    .testTag("switch_exam_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithPrimaryFixed)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.School, contentDescription = null, tint = ZenithPrimary)
                        Column {
                            Text(
                                text = "Active Exam: ${userProfile.selectedExam.displayName}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnPrimaryFixed)
                            )
                            Text(
                                text = "${userProfile.selectedExam.category} • Target ${userProfile.targetYear}",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnPrimaryFixed.copy(alpha = 0.8f))
                            )
                        }
                    }

                    Button(
                        onClick = { showExamDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Switch", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }

            // Prep Stats Summary (18 Mocks, 1250 Qs, 88.4% Acc)
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
                    Text("Prep Record & Benchmarks", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Mock Tests Completed", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("${userProfile.testsCompleted}", fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = FontFamily.Monospace)
                        }
                        Column {
                            Text("Questions Solved", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("${userProfile.totalQuestionsAttempted}", fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = FontFamily.Monospace)
                        }
                        Column {
                            Text("National Accuracy", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text("${userProfile.overallAccuracy}%", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = ZenithSecondary, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }

            // Cloud Sync Status (Firebase Integration)
            Surface(
                color = StatusAnsweredBg,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.CloudDone, contentDescription = null, tint = StatusAnswered, modifier = Modifier.size(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Cloud Persistence Active",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = StatusAnswered)
                        )
                        Text(
                            text = "User data & mock tests synchronized with Firebase Firestore",
                            style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant, fontSize = 11.sp)
                        )
                    }
                }
            }

            // Toggles
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
                    Text("Exam Preferences", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("NTA Exam Alerts & Notifications", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                            Text("Admit cards, answer keys & AIR rankings", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = ZenithPrimary)
                        )
                    }

                    HorizontalDivider(color = ZenithSurfaceContainerHigh)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Strict Proctored CBT Simulation", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                            Text("Simulate NTA test center integrity constraints", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }
                        Switch(
                            checked = strictCbtMode,
                            onCheckedChange = { strictCbtMode = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = ZenithPrimary)
                        )
                    }
                }
            }

            // Web Admin Panel Gateway Button (as required by PRD Section 27-31!)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenAdminPanel() }
                    .testTag("open_admin_panel_button"),
                color = ZenithTertiaryFixed,
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = ZenithOnTertiaryFixed)
                        Column {
                            Text(
                                text = "Faculty & Admin Panel",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnTertiaryFixed
                                )
                            )
                            Text(
                                text = "Question bank management, tests & security logs",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ZenithOnTertiaryFixed.copy(alpha = 0.8f),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = ZenithOnTertiaryFixed)
                }
            }

            // Sign Out
            OutlinedButton(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("sign_out_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusUnanswered)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text("Sign Out of Portal", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
