package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Question
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun BookmarksScreen(
    bookmarkedQuestions: List<Question>,
    onBack: () -> Unit,
    onToggleBookmark: (Question) -> Unit,
    onPracticeQuestion: (Question) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filtered = remember(bookmarkedQuestions, searchQuery, selectedFilter) {
        bookmarkedQuestions.filter { q ->
            val matchesSubject = when (selectedFilter) {
                "Physics" -> q.subject == com.example.model.Subject.PHYSICS
                "Chemistry" -> q.subject == com.example.model.Subject.CHEMISTRY
                "Biology" -> q.subject == com.example.model.Subject.BIOLOGY
                else -> true
            }
            val matchesSearch = if (searchQuery.isBlank()) true else {
                q.questionText.contains(searchQuery, ignoreCase = true) ||
                q.chapter.contains(searchQuery, ignoreCase = true) ||
                q.topic.contains(searchQuery, ignoreCase = true)
            }
            matchesSubject && matchesSearch
        }
    }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Saved Question Bank",
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bookmark_search_input"),
                placeholder = { Text("Search questions, chapters or formulas...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ZenithOnSurfaceVariant) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = ZenithSurfaceCard,
                    unfocusedContainerColor = ZenithSurfaceCard,
                    focusedBorderColor = BorderActive,
                    unfocusedBorderColor = BorderSubtle
                )
            )

            // Subject Filter Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Physics", "Chemistry", "Biology").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ZenithPrimaryFixed,
                            selectedLabelColor = ZenithPrimary
                        )
                    )
                }
            }

            // Results count
            Text(
                text = "${filtered.size} Questions in Your Revision Deck",
                style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant, fontWeight = FontWeight.Bold)
            )

            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = ZenithOutlineVariant, modifier = Modifier.size(48.dp))
                        Text("No bookmarked questions match your criteria", style = MaterialTheme.typography.bodyMedium.copy(color = ZenithOnSurfaceVariant))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { question ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("bookmark_card_${question.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = ZenithSurfaceContainerHigh,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "${question.subject.displayName} • ${question.chapter}",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ZenithPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }

                                    IconButton(
                                        onClick = { onToggleBookmark(question) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = "Remove Bookmark",
                                            tint = ZenithPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = question.questionText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        lineHeight = 20.sp
                                    )
                                )

                                if (question.formulaHighlight != null) {
                                    Surface(
                                        color = ZenithSurfaceSubtle,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = question.formulaHighlight,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ZenithPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        )
                                    }
                                }

                                HorizontalDivider(color = ZenithSurfaceContainerHigh)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = question.ncertReference,
                                        style = MaterialTheme.typography.labelSmall.copy(color = ZenithSecondary, fontWeight = FontWeight.SemiBold)
                                    )

                                    Button(
                                        onClick = { onPracticeQuestion(question) },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer)
                                    ) {
                                        Text("Practice", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
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
