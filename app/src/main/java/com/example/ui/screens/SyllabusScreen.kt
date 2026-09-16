package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.Subject
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun SyllabusScreen(
    onBack: () -> Unit,
    onStartChapterDrill: (String) -> Unit
) {
    var selectedSubject by remember { mutableStateOf(Subject.PHYSICS) }

    val chapters = remember(selectedSubject) {
        SampleData.sampleChapters.filter {
            if (selectedSubject == Subject.BIOLOGY) {
                it.subject == Subject.BIOLOGY || it.subject == Subject.BOTANY || it.subject == Subject.ZOOLOGY
            } else {
                it.subject == selectedSubject
            }
        }
    }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Syllabus & Chapter Drills",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Subject Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Subject.PHYSICS to "Physics",
                    Subject.CHEMISTRY to "Chemistry",
                    Subject.BIOLOGY to "Biology"
                ).forEach { (subj, label) ->
                    val isSelected = selectedSubject == subj
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedSubject = subj },
                        label = { Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ZenithPrimaryFixed,
                            selectedLabelColor = ZenithPrimary
                        ),
                        modifier = Modifier.testTag("filter_${label.lowercase()}")
                    )
                }
            }

            // Overview Banner
            Surface(
                color = ZenithSurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${selectedSubject.displayName} Syllabus",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ZenithPrimary)
                        )
                        Text(
                            text = "Class 11 & 12 NCERT Mapped Chapters",
                            style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                        )
                    }

                    Surface(
                        color = ZenithSecondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "NTA Pattern",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithOnSecondaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Chapter List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(chapters) { chapter ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onStartChapterDrill(chapter.chapterName) }
                            .testTag("chapter_card_${chapter.chapterName.take(6)}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = chapter.chapterName,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ZenithOnSurface),
                                    modifier = Modifier.weight(1f)
                                )

                                Surface(
                                    color = if (chapter.accuracy >= 75) StatusAnsweredBg else StatusUnansweredBg,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "${chapter.accuracy}% Acc",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (chapter.accuracy >= 75) StatusAnswered else StatusUnanswered,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${chapter.attemptedQuestions} / ${chapter.totalQuestions} Questions Attempted",
                                        style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                                    )
                                    Text(
                                        text = "${chapter.completionPercentage}% Completed",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }

                                LinearProgressIndicator(
                                    progress = { chapter.completionPercentage / 100f },
                                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                    color = ZenithPrimary,
                                    trackColor = ZenithSurfaceContainerHigh,
                                    strokeCap = StrokeCap.Round
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "30 MCQs per Drill • 45 Mins",
                                    style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontSize = 11.sp)
                                )

                                Button(
                                    onClick = { onStartChapterDrill(chapter.chapterName) },
                                    colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("Start Drill", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
