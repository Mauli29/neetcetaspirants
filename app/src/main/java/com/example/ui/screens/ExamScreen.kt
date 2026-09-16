package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Question
import com.example.model.QuestionPaletteState
import com.example.ui.ExamViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    onSubmitExam: () -> Unit
) {
    val questions by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val markedForReview by viewModel.markedForReview.collectAsState()
    val visitedQuestions by viewModel.visitedQuestions.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()
    val selectedSection by viewModel.selectedSection.collectAsState()
    val showSubmitDialog by viewModel.showSubmitDialog.collectAsState()
    val showPaletteDrawer by viewModel.showPaletteDrawer.collectAsState()

    // Find current question object or fallback to Q24
    val currentQuestion = questions.find { it.questionNumber == currentIndex }
        ?: questions.firstOrNull()
        ?: Question(
            id = "q_fallback",
            examType = com.example.model.ExamType.NEET,
            subject = com.example.model.Subject.PHYSICS,
            section = "Section A",
            chapter = "EM Waves",
            topic = "Wavelength in Dielectric",
            questionNumber = 24,
            questionText = "An electromagnetic wave of frequency ν = 3.0 MHz passes from vacuum into a dielectric medium with relative permittivity εᵣ = 4.0 and relative permeability μᵣ = 1.0. Then its wavelength in the medium is:",
            formulaHighlight = "ν = 3.0 MHz, εᵣ = 4.0, μᵣ = 1.0",
            conceptHint = "Wave Optics & EM Waves: Refractive index relation n = √(εᵣμᵣ) applies.",
            optionA = "Doubled and frequency remains unchanged",
            optionB = "Halved and frequency remains unchanged",
            optionC = "Doubled and frequency becomes half",
            optionD = "Remains unchanged",
            correctAnswer = 1,
            explanation = "n = √(εᵣ·μᵣ) = 2.0. λ' = λ/n = λ/2.",
            ncertReference = "NCERT Physics Part-II: Ch. 8, Pg 274"
        )

    val currentSelectedOption = selectedAnswers[currentIndex]
    val isMarkedForReview = markedForReview.contains(currentIndex)

    // Format timer hh:mm:ss
    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    // Palette modal sheet
    if (showPaletteDrawer) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closePaletteDrawer() },
            containerColor = ZenithSurfaceCard
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question Palette (200 Questions)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(onClick = { viewModel.closePaletteDrawer() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(StatusAnswered))
                        Text("Ans (${selectedAnswers.size})", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(StatusUnanswered))
                        Text("Not Ans (${(visitedQuestions - selectedAnswers.keys).size})", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(StatusReview))
                        Text("Review (${markedForReview.size})", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(StatusUnvisited))
                        Text("Not Visited (${200 - visitedQuestions.size})", style = MaterialTheme.typography.labelSmall)
                    }
                }

                // 200 Questions Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(200) { index ->
                        val qNum = index + 1
                        val isAns = selectedAnswers.containsKey(qNum)
                        val isRev = markedForReview.contains(qNum)
                        val isVis = visitedQuestions.contains(qNum)
                        val isCur = qNum == currentIndex

                        val bg = when {
                            isAns -> StatusAnswered
                            isRev -> StatusReview
                            isVis -> StatusUnanswered
                            else -> StatusUnvisitedBg
                        }
                        val textClr = if (isAns || isRev || isVis) Color.White else ZenithOnSurfaceVariant

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bg)
                                .border(
                                    width = if (isCur) 2.dp else 1.dp,
                                    color = if (isCur) BorderActive else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.selectQuestion(qNum)
                                    viewModel.closePaletteDrawer()
                                }
                                .testTag("palette_q_$qNum"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$qNum",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = textClr
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeSubmitDialog() },
            title = {
                Text(
                    text = "Submit Examination?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Review your response summary before final evaluation:",
                        style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                    )

                    Surface(
                        color = ZenithSurfaceSubtle,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Answered Questions:", style = MaterialTheme.typography.bodySmall)
                                Text("${selectedAnswers.size}", fontWeight = FontWeight.Bold, color = StatusAnswered)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Marked for Review:", style = MaterialTheme.typography.bodySmall)
                                Text("${markedForReview.size}", fontWeight = FontWeight.Bold, color = StatusReview)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Not Attempted:", style = MaterialTheme.typography.bodySmall)
                                Text("${200 - selectedAnswers.size}", fontWeight = FontWeight.Bold, color = StatusUnanswered)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Time Remaining:", style = MaterialTheme.typography.bodySmall)
                                Text(formattedTime, fontWeight = FontWeight.Bold, color = ZenithPrimary)
                            }
                        }
                    }

                    Text(
                        text = "Once submitted, your answers will be calibrated against All-India benchmarks and scorecards generated.",
                        style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant, fontSize = 11.sp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.confirmSubmitExam()
                        onSubmitExam()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("confirm_submit_test_button")
                ) {
                    Text("Yes, Submit Test", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.closeSubmitDialog() }) {
                    Text("Return to Test", color = ZenithOnSurfaceVariant)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            // Sticky Proctored Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ZenithSurfaceCard,
                shadowElevation = 3.dp
            ) {
                Column(modifier = Modifier.statusBarsPadding()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left: Palette Toggle Button
                        Button(
                            onClick = { viewModel.openPaletteDrawer() },
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithSurfaceContainerHigh),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("palette_drawer_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.GridOn, contentDescription = null, tint = ZenithPrimary, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Q $currentIndex/200",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Center: Exam Title & Mode
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "NEET Full Mock #14",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(StatusAnswered))
                                Text(
                                    text = "NTA CBT Strict Mode",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusAnswered,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        // Right: Live Countdown Timer
                        Surface(
                            color = ZenithSurfaceContainerLow,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BorderSubtle)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Timer",
                                    tint = if (remainingSeconds < 900) TimerCritical else ZenithPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = formattedTime,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (remainingSeconds < 900) TimerCritical else ZenithPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    modifier = Modifier.testTag("exam_countdown_timer")
                                )
                            }
                        }
                    }

                    // Section Tabs: Physics (Sec A), Chemistry, Botany, Zoology
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "Physics (Sec A)" to "24/35",
                            "Chemistry" to "0/50",
                            "Botany" to "0/50",
                            "Zoology" to "0/50"
                        ).forEach { (secTitle, progress) ->
                            val isSelected = selectedSection == secTitle
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.selectSection(secTitle) }
                                    .testTag("sec_tab_${secTitle.take(4)}"),
                                color = if (isSelected) ZenithPrimaryFixed else ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(6.dp),
                                border = if (isSelected) BorderStroke(1.dp, ZenithPrimary) else null
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = secTitle.split(" ").first(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isSelected) ZenithPrimary else ZenithOnSurfaceVariant,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 11.sp
                                        ),
                                        maxLines = 1
                                    )
                                    Text(
                                        text = progress,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isSelected) ZenithPrimary else ZenithOnSurfaceVariant.copy(alpha = 0.6f),
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        },
        bottomBar = {
            // Sticky Action Dock & Response Matrix
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ZenithSurfaceCard,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Response Matrix Summary Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusAnswered))
                            Text("18 Answered", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontWeight = FontWeight.Bold))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusUnanswered))
                            Text("4 Unanswered", style = MaterialTheme.typography.labelSmall.copy(color = StatusUnanswered, fontWeight = FontWeight.Bold))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusReview))
                            Text("2 Review", style = MaterialTheme.typography.labelSmall.copy(color = StatusReview, fontWeight = FontWeight.Bold))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StatusUnvisited))
                            Text("176 Left", style = MaterialTheme.typography.labelSmall.copy(color = StatusUnvisited, fontWeight = FontWeight.Bold))
                        }
                    }

                    // Action Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Mark for Review Button
                        OutlinedButton(
                            onClick = { viewModel.toggleMarkReview() },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isMarkedForReview) StatusReviewBg else ZenithSurfaceCard,
                                contentColor = if (isMarkedForReview) StatusReview else ZenithOnSurfaceVariant
                            ),
                            border = BorderStroke(1.dp, if (isMarkedForReview) StatusReview else BorderSubtle),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("button_mark_review")
                        ) {
                            Icon(
                                imageVector = if (isMarkedForReview) Icons.Default.BookmarkAdded else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Review", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        }

                        // Clear Button
                        OutlinedButton(
                            onClick = { viewModel.clearOption() },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = ZenithSurfaceCard,
                                contentColor = ZenithOnSurfaceVariant
                            ),
                            border = BorderStroke(1.dp, BorderSubtle),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("button_clear_option")
                        ) {
                            Text("Clear", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        }

                        // Save & Next Button
                        Button(
                            onClick = { viewModel.saveAndNext() },
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("button_save_next")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("Save & Next", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }

                        // Submit Test
                        Button(
                            onClick = { viewModel.openSubmitDialog() },
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithSecondary),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                            modifier = Modifier.testTag("button_submit_exam")
                        ) {
                            Text("Submit", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.White))
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
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Main Question Display Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("question_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Question Header & Meta Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Question $currentIndex",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )

                            Surface(
                                color = ZenithSurfaceContainerHigh,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Single Choice",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                color = StatusAnsweredBg,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "+4.0 / -1.0",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusAnswered,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                            }

                            IconButton(
                                onClick = { viewModel.repository.toggleBookmark(currentQuestion) },
                                modifier = Modifier.size(32.dp).testTag("bookmark_question_button")
                            ) {
                                Icon(
                                    imageVector = if (currentQuestion.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (currentQuestion.isBookmarked) ZenithPrimary else ZenithOnSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // Question Text
                    Text(
                        text = currentQuestion.questionText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ZenithOnSurface,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    // Formula Highlight Box
                    if (currentQuestion.formulaHighlight != null) {
                        Surface(
                            color = ZenithSurfaceSubtle,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Functions,
                                    contentDescription = null,
                                    tint = ZenithPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = currentQuestion.formulaHighlight,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                            }
                        }
                    }

                    // Concept Hint Drawer / Box
                    if (currentQuestion.conceptHint != null) {
                        Surface(
                            color = ZenithTertiaryFixed.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = ZenithOnTertiaryFixed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = currentQuestion.conceptHint,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = ZenithOnTertiaryFixed,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = ZenithSurfaceContainerHigh)

                    // Options A, B, C, D
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf(
                            0 to currentQuestion.optionA,
                            1 to currentQuestion.optionB,
                            2 to currentQuestion.optionC,
                            3 to currentQuestion.optionD
                        ).forEach { (optIndex, optText) ->
                            val isSelected = currentSelectedOption == optIndex
                            val letter = ('A'.code + optIndex).toChar().toString()

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.selectOption(optIndex) }
                                    .testTag("option_$letter"),
                                color = if (isSelected) ZenithPrimaryFixed else ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) ZenithPrimary else BorderSubtle
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Letter Badge
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) ZenithPrimary else ZenithSurfaceCard)
                                            .border(1.dp, if (isSelected) ZenithPrimary else BorderSubtle, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = letter,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = if (isSelected) Color.White else ZenithOnSurface,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }

                                    // Option Text
                                    Text(
                                        text = optText,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = if (isSelected) ZenithOnPrimaryFixed else ZenithOnSurface,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Radio Indicator
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { viewModel.selectOption(optIndex) },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = ZenithPrimary,
                                            unselectedColor = ZenithOutlineVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Nav Row (Previous & Next question jump)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { viewModel.previousQuestion() },
                    enabled = currentIndex > 1,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Previous Q")
                }

                Text(
                    text = "Section A: 35 Mandatory Questions",
                    style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                )

                OutlinedButton(
                    onClick = { viewModel.saveAndNext() },
                    enabled = currentIndex < 200,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Next Q")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
