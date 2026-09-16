package com.example.model

enum class ExamType(val displayName: String, val category: String) {
    NEET("NEET UG", "Medical (MBBS / BDS)"),
    JEE_MAIN("JEE Main", "Engineering (B.Tech / B.E.)"),
    JEE_ADVANCED("JEE Advanced", "IIT Track"),
    MHT_CET("MHT-CET", "State Engineering & Pharmacy")
}

enum class Subject(val displayName: String, val iconName: String) {
    PHYSICS("Physics", "bolt"),
    CHEMISTRY("Chemistry", "science"),
    BIOLOGY("Biology (Botany + Zoology)", "eco"),
    BOTANY("Botany", "psychology_alt"),
    ZOOLOGY("Zoology", "cruelty_free"),
    MATHEMATICS("Mathematics", "functions")
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}

enum class QuestionPaletteState {
    ANSWERED,
    NOT_ANSWERED,
    MARKED_REVIEW,
    ANSWERED_AND_REVIEW,
    NOT_VISITED
}

data class Question(
    val id: String,
    val examType: ExamType,
    val subject: Subject,
    val section: String = "Section A", // "Section A" (35) or "Section B" (15)
    val chapter: String,
    val topic: String,
    val questionNumber: Int,
    val questionText: String,
    val formulaHighlight: String? = null,
    val conceptHint: String? = null,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: Int, // 0 = A, 1 = B, 2 = C, 3 = D
    val explanation: String,
    val ncertReference: String,
    val marks: Double = 4.0,
    val negativeMarks: Double = 1.0,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val isBookmarked: Boolean = false
)

data class TestOption(
    val id: String,
    val title: String,
    val examType: ExamType,
    val questionCount: Int,
    val durationMinutes: Int,
    val totalMarks: Int,
    val isLive: Boolean = false,
    val startsIn: String? = null,
    val enrolledCount: String = "12,450+ registered"
)

data class TestAttemptResult(
    val id: String,
    val testTitle: String,
    val examType: ExamType,
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
    val timestamp: Long = System.currentTimeMillis()
)

data class ChapterProgress(
    val chapterName: String,
    val subject: Subject,
    val totalQuestions: Int,
    val attemptedQuestions: Int,
    val accuracy: Int,
    val completionPercentage: Int
)

data class UserProfile(
    val uid: String = "user_ananya_01",
    val fullName: String = "Ananya Sharma",
    val email: String = "ananya.sharma@example.com",
    val mobileNumber: String = "9876543210",
    val selectedExam: ExamType = ExamType.NEET,
    val targetYear: String = "2025",
    val academicLevel: String = "Class 12 / Dropper",
    val stateDomicile: String = "Maharashtra (MH-CET / State Quota)",
    val studyStreakDays: Int = 14,
    val averageScore: Int = 618,
    val totalQuestionsAttempted: Int = 1250,
    val testsCompleted: Int = 18,
    val overallAccuracy: Double = 88.4,
    val allIndiaRankPercentile: Double = 98.2
)
