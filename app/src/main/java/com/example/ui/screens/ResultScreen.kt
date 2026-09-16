package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TestAttemptResult
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun ResultScreen(
    result: TestAttemptResult,
    onReturnHome: () -> Unit,
    onReviewSolutions: () -> Unit
) {
    var mistakeFilter by remember { mutableStateOf("all") } // "all", "phy", "chem", "bio"

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Exam Result & Analytics",
                onBack = onReturnHome,
                showProfile = true
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ZenithSurfaceCard,
                tonalElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onReturnHome,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("return_home_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Dashboard", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onReviewSolutions,
                        modifier = Modifier
                            .weight(1.4f)
                            .height(48.dp)
                            .testTag("review_solutions_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("View Solutions", fontWeight = FontWeight.Bold, color = Color.White)
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
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
            // Celebration Badge
            Surface(
                color = ZenithSecondaryContainer,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "🎉", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "OUTSTANDING! MEDICAL COLLEGE RANK QUALIFIED",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ZenithOnSecondaryContainer,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }

            // Big Score Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("score_hero_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "TOTAL MOCK SCORE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ZenithOnSurfaceVariant,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${result.score}",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = ZenithPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = " / ${result.maxScore}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = ZenithOnSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Text(
                        text = "Top 0.8% benchmark reached. Exceptional retention in plant physiology & organic mechanisms!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ZenithOnSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    HorizontalDivider(color = ZenithSurfaceContainerHigh, modifier = Modifier.padding(vertical = 4.dp))

                    // 3-Bento Stats (AIR, Percentile, Accuracy)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("AIR Pred.", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text(
                                text = "#${result.airRankPrediction}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithPrimary,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }

                        VerticalDivider(modifier = Modifier.height(32.dp), color = ZenithSurfaceContainerHigh)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Percentile", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text(
                                text = "${result.percentile}%",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithSecondary,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }

                        VerticalDivider(modifier = Modifier.height(32.dp), color = ZenithSurfaceContainerHigh)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Accuracy", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                            Text(
                                text = "${result.accuracyPercentage}%",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = StatusReview,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }
                }
            }

            // Question Distribution & Donut Chart
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
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Question Distribution",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Donut Chart Canvas
                        Box(
                            modifier = Modifier.size(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(90.dp)) {
                                val strokeWidth = 14.dp.toPx()
                                val total = 200f
                                val correctSweep = (165f / total) * 360f
                                val incorrectSweep = (12f / total) * 360f
                                val skippedSweep = (23f / total) * 360f

                                // Skipped Arc
                                drawArc(
                                    color = StatusUnvisited,
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )
                                // Incorrect Arc
                                drawArc(
                                    color = StatusUnanswered,
                                    startAngle = -90f + correctSweep,
                                    sweepAngle = incorrectSweep,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )
                                // Correct Arc
                                drawArc(
                                    color = StatusAnswered,
                                    startAngle = -90f,
                                    sweepAngle = correctSweep,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${result.attemptedQuestions}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                                Text(
                                    text = "Attempted",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithOnSurfaceVariant,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }

                        // Distribution Breakdown Rows
                        Column(
                            modifier = Modifier.weight(1f).padding(start = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusAnswered))
                                    Text("Correct (${result.correctCount})", style = MaterialTheme.typography.bodySmall)
                                }
                                Text("+660", fontWeight = FontWeight.Bold, color = StatusAnswered, fontFamily = FontFamily.Monospace)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusUnanswered))
                                    Text("Incorrect (${result.incorrectCount})", style = MaterialTheme.typography.bodySmall)
                                }
                                Text("-12", fontWeight = FontWeight.Bold, color = StatusUnanswered, fontFamily = FontFamily.Monospace)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusUnvisited))
                                    Text("Skipped (${result.unattemptedCount})", style = MaterialTheme.typography.bodySmall)
                                }
                                Text("0", fontWeight = FontWeight.Bold, color = StatusUnvisited, fontFamily = FontFamily.Monospace)
                            }

                            Surface(
                                color = ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.Bolt, contentDescription = null, tint = ZenithPrimary, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = "Time Velocity: 2h 54m (58s / Q avg)",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Subject Performance Breakdown
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
                        text = "Subject Performance",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ZenithOnSurface
                        )
                    )

                    // Physics
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Physics: 156 / 180", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            Text("Needs Attention", style = MaterialTheme.typography.labelSmall.copy(color = TimerWarning, fontWeight = FontWeight.Bold))
                        }
                        LinearProgressIndicator(
                            progress = { 156f / 180f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = TimerWarning,
                            trackColor = ZenithSurfaceContainerHigh,
                            strokeCap = StrokeCap.Round
                        )
                        Text("42 Attempted • 88% Accuracy", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontSize = 10.sp))
                    }

                    // Chemistry
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Chemistry: 168 / 180", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            Text("Strong Performer", style = MaterialTheme.typography.labelSmall.copy(color = ZenithSecondary, fontWeight = FontWeight.Bold))
                        }
                        LinearProgressIndicator(
                            progress = { 168f / 180f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = ZenithSecondary,
                            trackColor = ZenithSurfaceContainerHigh,
                            strokeCap = StrokeCap.Round
                        )
                        Text("43 Attempted • 94% Accuracy", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontSize = 10.sp))
                    }

                    // Biology
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Biology (Botany + Zoology): 328 / 360", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            Text("Mastery Level", style = MaterialTheme.typography.labelSmall.copy(color = ZenithPrimary, fontWeight = FontWeight.Bold))
                        }
                        LinearProgressIndicator(
                            progress = { 328f / 360f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = ZenithPrimary,
                            trackColor = ZenithSurfaceContainerHigh,
                            strokeCap = StrokeCap.Round
                        )
                        Text("87 Attempted • 96% Accuracy", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontSize = 10.sp))
                    }
                }
            }

            // High Yield Mistake Review
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "High-Yield Mistake Review",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ZenithOnSurface
                            )
                        )
                        Surface(
                            color = StatusUnansweredBg,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "12 Incorrect Questions",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = StatusUnanswered,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    // Mistake Topic Card
                    Surface(
                        color = ZenithSurfaceSubtle,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Electrostatics & Gauss Law",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "3 Slips",
                                    style = MaterialTheme.typography.labelSmall.copy(color = StatusUnanswered, fontWeight = FontWeight.Bold)
                                )
                            }
                            Text(
                                text = "Conceptual slips on solid angle flux relations and permittivity calculations in dielectric medium.",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "NCERT Physics Part-I: Ch. 1, Pg 32",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                TextButton(
                                    onClick = onReviewSolutions,
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Review 3 Qs ->", fontWeight = FontWeight.Bold, color = ZenithPrimary)
                                }
                            }
                        }
                    }

                    // 5-Min NCERT Concept Booster
                    Surface(
                        color = StatusReviewBg,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(StatusReview),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "5-Min NCERT Concept Booster",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface)
                                )
                                Text(
                                    text = "Gauss Law & Dielectric Boundary conditions",
                                    style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                                )
                            }
                        }
                    }
                }
            }

            // State Rank Banner
            Surface(
                color = ZenithSurfaceContainerLow,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = ZenithPrimary, modifier = Modifier.size(24.dp))
                    Column {
                        Text(
                            text = "State Rank: Top 45",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithPrimary)
                        )
                        Text(
                            text = "Out of 28,400 test takers in Maharashtra (State Quota Benchmarked)",
                            style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                        )
                    }
                }
            }
        }
    }
}
