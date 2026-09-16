package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.model.Difficulty
import com.example.model.ExamType
import com.example.model.Question
import com.example.model.Subject
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun AdminPanelScreen(
    securityLogs: List<String>,
    onBack: () -> Unit,
    onAddQuestion: (Question) -> Unit
) {
    var activeTab by remember { mutableStateOf("dashboard") } // "dashboard", "add_question", "security"

    // Form inputs for new question
    var newQuestionText by remember { mutableStateOf("") }
    var newSubject by remember { mutableStateOf(Subject.PHYSICS) }
    var newChapter by remember { mutableStateOf("Optics & Ray Optics") }
    var newFormula by remember { mutableStateOf("1/f = 1/v - 1/u") }
    var newOptionA by remember { mutableStateOf("f/2") }
    var newOptionB by remember { mutableStateOf("2f") }
    var newOptionC by remember { mutableStateOf("4f") }
    var newOptionD by remember { mutableStateOf("f") }
    var newCorrectOpt by remember { mutableIntStateOf(1) } // B
    var newNcertRef by remember { mutableStateOf("NCERT Physics Part-II: Ch. 9, Pg 310") }
    var isQuestionSaved by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Faculty & Admin Portal",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Admin Navigation Tabs
            Surface(
                color = ZenithSurfaceSubtle,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(
                        onClick = { activeTab = "dashboard" },
                        modifier = Modifier.weight(1f).height(38.dp).testTag("admin_tab_dashboard"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (activeTab == "dashboard") ZenithSurfaceCard else Color.Transparent,
                            contentColor = if (activeTab == "dashboard") ZenithPrimary else ZenithOnSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = if (activeTab == "dashboard") ButtonDefaults.buttonElevation(1.dp) else ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text("Overview", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }

                    Button(
                        onClick = { activeTab = "add_question" },
                        modifier = Modifier.weight(1f).height(38.dp).testTag("admin_tab_add_question"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (activeTab == "add_question") ZenithSurfaceCard else Color.Transparent,
                            contentColor = if (activeTab == "add_question") ZenithPrimary else ZenithOnSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = if (activeTab == "add_question") ButtonDefaults.buttonElevation(1.dp) else ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text("+ Add MCQ", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }

                    Button(
                        onClick = { activeTab = "security" },
                        modifier = Modifier.weight(1f).height(38.dp).testTag("admin_tab_security"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (activeTab == "security") ZenithSurfaceCard else Color.Transparent,
                            contentColor = if (activeTab == "security") ZenithPrimary else ZenithOnSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = if (activeTab == "security") ButtonDefaults.buttonElevation(1.dp) else ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text("Proctor Logs", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }

            when (activeTab) {
                "dashboard" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("National Platform Metrics", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        // 4-Card Bento
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Registered Students", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                    Text("104,250", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = ZenithPrimary, fontFamily = FontFamily.Monospace)
                                    Text("+1,200 today", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontSize = 10.sp))
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Live Test Takers", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                    Text("3,420", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = ZenithSecondary, fontFamily = FontFamily.Monospace)
                                    Text("Active in 84 Mocks", style = MaterialTheme.typography.labelSmall.copy(color = ZenithSecondary, fontSize = 10.sp))
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Question Bank", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                    Text("18,500 MCQs", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = ZenithTertiary, fontFamily = FontFamily.Monospace)
                                    Text("100% NCERT Mapped", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontSize = 10.sp))
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Integrity Score", style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                    Text("99.8%", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = StatusAnswered, fontFamily = FontFamily.Monospace)
                                    Text("Anti-cheat Active", style = MaterialTheme.typography.labelSmall.copy(color = StatusAnswered, fontSize = 10.sp))
                                }
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Live Test Series Management", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                                listOf(
                                    "NEET Grand Mock #14" to "Live (12,450 enrolled) • Closes in 04h:22m",
                                    "MHT-CET Full PCM Drill #06" to "Scheduled for Tomorrow 10:00 AM",
                                    "JEE Main 2025 Paper 1 Simulation" to "Active (8,200 enrolled)"
                                ).forEach { (testTitle, status) ->
                                    Surface(
                                        color = ZenithSurfaceSubtle,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(testTitle, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                                Text(status, style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant))
                                            }
                                            Icon(Icons.Default.MoreVert, contentDescription = null, tint = ZenithOnSurfaceVariant)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "add_question" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Create & Publish New Question", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        if (isQuestionSaved) {
                            Surface(
                                color = StatusAnsweredBg,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Question published successfully to Question Bank!",
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall.copy(color = StatusAnswered, fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        OutlinedTextField(
                            value = newQuestionText,
                            onValueChange = { newQuestionText = it },
                            modifier = Modifier.fillMaxWidth().testTag("admin_question_text"),
                            placeholder = { Text("Enter question statement...") },
                            label = { Text("Question Text *") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = newFormula,
                            onValueChange = { newFormula = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Formula highlight (e.g. 1/f = 1/v - 1/u)") },
                            label = { Text("Key Formula") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newOptionA,
                                onValueChange = { newOptionA = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Option A") },
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = newOptionB,
                                onValueChange = { newOptionB = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Option B") },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newOptionC,
                                onValueChange = { newOptionC = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Option C") },
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = newOptionD,
                                onValueChange = { newOptionD = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Option D") },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        OutlinedTextField(
                            value = newNcertRef,
                            onValueChange = { newNcertRef = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("NCERT Reference & Page Number") },
                            shape = RoundedCornerShape(10.dp)
                        )

                        Button(
                            onClick = {
                                val q = Question(
                                    id = "q_custom_${System.currentTimeMillis()}",
                                    examType = ExamType.NEET,
                                    subject = newSubject,
                                    section = "Section A",
                                    chapter = newChapter,
                                    topic = "Optics",
                                    questionNumber = 25,
                                    questionText = if (newQuestionText.isBlank()) "What is the focal length of a combination of two thin lenses in contact?" else newQuestionText,
                                    formulaHighlight = newFormula,
                                    optionA = newOptionA,
                                    optionB = newOptionB,
                                    optionC = newOptionC,
                                    optionD = newOptionD,
                                    correctAnswer = newCorrectOpt,
                                    explanation = "1/F = 1/f1 + 1/f2",
                                    ncertReference = newNcertRef
                                )
                                onAddQuestion(q)
                                isQuestionSaved = true
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("publish_question_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Publish to National Question Bank", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                "security" -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Live Proctored Event Log", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        if (securityLogs.isEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.GppGood, contentDescription = null, tint = StatusAnswered, modifier = Modifier.size(36.dp))
                                    Text("No Integrity Violations Recorded", fontWeight = FontWeight.Bold)
                                    Text("Test session is strictly compliant with NTA CBT regulations.", style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant))
                                }
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(securityLogs) { log ->
                                    Surface(
                                        color = StatusUnansweredBg,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(Icons.Default.Warning, contentDescription = null, tint = StatusUnanswered, modifier = Modifier.size(18.dp))
                                            Text(log, style = MaterialTheme.typography.bodySmall.copy(color = StatusUnanswered, fontFamily = FontFamily.Monospace))
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
}
