package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val examType: String,
    val subject: String,
    val section: String,
    val chapter: String,
    val topic: String,
    val questionNumber: Int,
    val questionText: String,
    val formulaHighlight: String?,
    val conceptHint: String?,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: Int,
    val explanation: String,
    val ncertReference: String,
    val marks: Double,
    val negativeMarks: Double,
    val difficulty: String,
    val isBookmarked: Boolean = false
)

@Entity(tableName = "test_attempts")
data class TestAttemptEntity(
    @PrimaryKey val id: String,
    val testTitle: String,
    val examType: String,
    val score: Int,
    val maxScore: Int,
    val accuracyPercentage: Double,
    val airRankPrediction: Int,
    val percentile: Double,
    val attemptedQuestions: Int,
    val totalQuestions: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val timeTakenSeconds: Long,
    val physicsScore: Int,
    val chemistryScore: Int,
    val biologyScore: Int,
    val timestamp: Long
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val questionId: String,
    val examType: String,
    val subject: String,
    val chapter: String,
    val questionText: String,
    val savedAt: Long = System.currentTimeMillis()
)
