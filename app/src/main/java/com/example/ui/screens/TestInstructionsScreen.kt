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
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun TestInstructionsScreen(
    onBack: () -> Unit,
    onStartExam: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("en") } // "en" or "hi"
    var declarationAccepted by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Test Instructions",
                onBack = onBack,
                showProfile = true
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ZenithSurfaceCard,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onStartExam,
                        enabled = declarationAccepted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("begin_test_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZenithPrimary,
                            disabledContainerColor = ZenithOutlineVariant
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "I Am Ready to Begin Test",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
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
            // Test Meta Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = ZenithSurfaceContainerHigh,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "NTA CBT Pattern",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Surface(
                            color = ZenithSecondaryContainer.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = ZenithSecondary, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "Strict Proctored",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "All India NEET Full Mock 14",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithOnSurface
                            )
                        )
                        Text(
                            text = "Simulated National Entrance Test • Cycle 2025 Standard Format",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ZenithOnSurfaceVariant)
                        )
                    }

                    // Subject Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Physics", "Chemistry", "Botany", "Zoology").forEach { subj ->
                            Surface(
                                color = ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = subj,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithOnSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // 3-Column Bento Summary Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Questions
                Surface(
                    modifier = Modifier.weight(1f),
                    color = ZenithPrimaryFixed,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "180 / 200",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithOnPrimaryFixed,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "Attempt Qs",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnPrimaryFixed)
                        )
                    }
                }

                // Marks
                Surface(
                    modifier = Modifier.weight(1f),
                    color = ZenithSecondaryFixed,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "720",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithOnSecondaryFixedVariant,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "Total Marks",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSecondaryFixedVariant)
                        )
                    }
                }

                // Duration
                Surface(
                    modifier = Modifier.weight(1f),
                    color = ZenithTertiaryFixed,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "200 Mins",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithOnTertiaryFixed,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "Duration",
                            style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnTertiaryFixed)
                        )
                    }
                }
            }

            // Marking Scheme
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Official NTA Marking Scheme",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Correct
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = StatusAnsweredBg,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "+4.0",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = StatusAnswered,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                                Text(
                                    text = "Correct Answer",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusAnswered,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        // Incorrect
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = StatusUnansweredBg,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "-1.0",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = StatusUnanswered,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                                Text(
                                    text = "Negative Mark",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusUnanswered,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        // Left
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = StatusUnvisitedBg,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "0.0",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = StatusUnvisited,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                                Text(
                                    text = "Unattempted",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusUnvisited,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Question Palette States Legend
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Question Palette Legend",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Answered
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(6.dp)).background(StatusAnswered),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("01", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("Answered", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }

                        // Not Answered
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(6.dp)).background(StatusUnanswered),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("02", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("Not Answered", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }

                        // Marked Review
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(6.dp)).background(StatusReview),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("03", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("Review", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }

                        // Not Visited
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(6.dp)).background(StatusUnvisited),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("04", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("Not Visited", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                        }
                    }
                }
            }

            // CBT Navigation Rules
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Important Examination Protocol",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )

                    listOf(
                        "1. Section Bifurcation: Section A contains 35 mandatory questions. Section B contains 15 questions, where you need to attempt any 10.",
                        "2. Strict Proctored Environment: Switching apps or losing window focus is tracked. 3 violations will trigger automatic proctor review.",
                        "3. Auto-Submission: The examination will automatically submit when the countdown reaches 00:00:00. Ensure all answers are saved using 'Save & Next'."
                    ).forEach { rule ->
                        Text(
                            text = rule,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ZenithOnSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            // Language Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
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
                    Text(
                        text = "Test Medium (Language):",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedLanguage == "en",
                            onClick = { selectedLanguage = "en" },
                            label = { Text("English") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ZenithPrimaryFixed,
                                selectedLabelColor = ZenithPrimary
                            )
                        )
                        FilterChip(
                            selected = selectedLanguage == "hi",
                            onClick = { selectedLanguage = "hi" },
                            label = { Text("हिंदी (Hindi)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ZenithPrimaryFixed,
                                selectedLabelColor = ZenithPrimary
                            )
                        )
                    }
                }
            }

            // Candidate Declaration Checkbox
            Surface(
                color = ZenithSurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable { declarationAccepted = !declarationAccepted },
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Checkbox(
                        checked = declarationAccepted,
                        onCheckedChange = { declarationAccepted = it },
                        colors = CheckboxDefaults.colors(checkedColor = ZenithPrimary),
                        modifier = Modifier.size(20.dp).testTag("declaration_checkbox")
                    )
                    Text(
                        text = "I have read and understood all the instructions above. I confirm that I am not in possession of any unauthorized materials and will abide by the NTA CBT code of conduct.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ZenithOnSurface,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}
