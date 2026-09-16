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
import androidx.compose.ui.graphics.Brush
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
fun HomeScreen(
    userProfile: UserProfile,
    onStartTest: () -> Unit,
    onViewResult: () -> Unit,
    onNavigateSyllabus: () -> Unit,
    onNavigateAnalytics: () -> Unit,
    onNavigateBookmarks: () -> Unit,
    onNavigateProfile: () -> Unit,
    onSwitchExam: (ExamType) -> Unit
) {
    var showExamSwitcher by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Home Dashboard",
                onBack = null,
                showProfile = true,
                showNotifications = true,
                examTag = "${userProfile.selectedExam.displayName} '25",
                onProfileClick = onNavigateProfile,
                onNotificationClick = { /* Show announcements */ }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = ZenithSurfaceCard,
                tonalElevation = 6.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ZenithPrimary,
                        selectedTextColor = ZenithPrimary,
                        indicatorColor = ZenithPrimaryFixed
                    ),
                    modifier = Modifier.testTag("nav_home")
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateSyllabus,
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Syllabus") },
                    label = { Text("Syllabus") },
                    modifier = Modifier.testTag("nav_syllabus")
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateAnalytics,
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Analytics") },
                    label = { Text("Analytics") },
                    modifier = Modifier.testTag("nav_analytics")
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateBookmarks,
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks") },
                    label = { Text("Saved") },
                    modifier = Modifier.testTag("nav_bookmarks")
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateProfile,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    modifier = Modifier.testTag("nav_profile")
                )
            }
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
            // Student Welcome & Countdown Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, ${userProfile.fullName.split(" ").firstOrNull() ?: "Aspirant"} 👋",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = StatusUnansweredBg,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${userProfile.selectedExam.displayName} 2025 • 48 Days Left",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = StatusUnanswered,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                // Streak Flame Card
                Surface(
                    color = ZenithSurfaceContainerLow,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.clickable { /* Streak history */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "🔥", fontSize = 16.sp)
                        Column {
                            Text(
                                text = "${userProfile.studyStreakDays} Days",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithPrimary
                                )
                            )
                            Text(
                                text = "Daily Streak",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithOnSurfaceVariant,
                                    fontSize = 9.sp
                                )
                            )
                        }
                    }
                }
            }

            // Featured Hero Card (All-India Grand Mock #14)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("featured_test_card"),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(ZenithPrimary, Color(0xFF1E40AF), Color(0xFF001453))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Top row badges: Live & Countdown
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = StatusAnswered,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    )
                                    Text(
                                        text = "FREE LIVE TEST",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        )
                                    )
                                }
                            }

                            Surface(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = null,
                                        tint = ZenithSecondaryFixed,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Starts in 02:14:38",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithSecondaryFixed,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    )
                                }
                            }
                        }

                        // Test Title & Subtitle
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "RECOMMENDED NATIONAL BENCHMARK",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithPrimaryFixed,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    fontSize = 10.sp
                                )
                            )
                            Text(
                                text = "All India Full Mock Test #14",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Full Syllabus Simulation • NTA 2025 Standard Pattern with Sections A & B",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ZenithPrimaryFixed.copy(alpha = 0.85f)
                                )
                            )
                        }

                        // Exam Specs Pill Row
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf("720 Marks", "200 Qs", "3h 20m").forEach { spec ->
                                Surface(
                                    color = Color.White.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = spec,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

                        // Participant stats and Start Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = ZenithSecondaryFixed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "12,450+ registered",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithSecondaryFixed,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Button(
                                onClick = onStartTest,
                                modifier = Modifier.testTag("start_mock_cta"),
                                colors = ButtonDefaults.buttonColors(containerColor = ZenithSecondaryFixed),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Start Now",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ZenithOnSecondaryFixedVariant
                                        )
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = ZenithOnSecondaryFixedVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Bento Metrics Row
            Text(
                text = "Performance Metrics",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ZenithOnSurface
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Avg Score
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Avg. Score",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                        )
                        Text(
                            text = "${userProfile.averageScore}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = StatusAnswered, modifier = Modifier.size(14.dp))
                            Text(
                                text = "+14 pts",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = StatusAnswered,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }

                // Accuracy
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Accuracy",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                        )
                        Text(
                            text = "${userProfile.overallAccuracy}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "Top 2% cohort",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // All India Rank Prediction
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "AIR Pred.",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                        )
                        Text(
                            text = "${userProfile.allIndiaRankPercentile}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = StatusReview,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "~2,420 AIR",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = StatusReview,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            // Practice & Test Series Grid (2x2)
            Text(
                text = "Practice & Test Series",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ZenithOnSurface
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Full Syllabus Mocks
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onStartTest() }
                        .testTag("card_full_mocks"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ZenithPrimaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Assignment, contentDescription = null, tint = ZenithPrimary)
                        }
                        Column {
                            Text(
                                text = "Full Syllabus Mocks",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "24 NTA Tests • Timed",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }
                }

                // Subject-wise Tests
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateSyllabus() }
                        .testTag("card_subject_tests"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ZenithSecondaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = ZenithSecondary)
                        }
                        Column {
                            Text(
                                text = "Subject Tests",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "60 Tests • Phy, Chem, Bio",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Chapter Drills
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateSyllabus() }
                        .testTag("card_chapter_drills"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ZenithTertiaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Biotech, contentDescription = null, tint = ZenithTertiary)
                        }
                        Column {
                            Text(
                                text = "Chapter Drills",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "180 Topic Drills",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }
                }

                // Previous Years Papers (PYQs)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onStartTest() }
                        .testTag("card_pyqs"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ZenithSurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.HistoryEdu, contentDescription = null, tint = ZenithPrimary)
                        }
                        Column {
                            Text(
                                text = "Previous Papers",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "2019-2024 PYQs + Sol.",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }
                }
            }

            // Recent Attempt Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Test Attempt",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ZenithOnSurface
                    )
                )
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ZenithPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { onViewResult() }
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(StatusAnsweredBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.FactCheck, contentDescription = null, tint = StatusAnswered)
                        }
                        Column {
                            Text(
                                text = "NEET Biology Unit Drill 04",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Score: 320 / 360 • Accuracy: 92%",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onViewResult,
                        modifier = Modifier.testTag("review_recent_attempt_button"),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Review", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}
