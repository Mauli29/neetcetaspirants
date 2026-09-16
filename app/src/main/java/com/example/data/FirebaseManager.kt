package com.example.data

import android.content.Context
import android.util.Log
import com.example.model.TestAttemptResult
import com.example.model.UserProfile
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseManager(private val context: Context) {
    private val tag = "ZenithFirebase"
    private var isFirebaseAvailable = false
    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null

    init {
        try {
            val app = if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            } else {
                FirebaseApp.getInstance()
            }
            if (app != null) {
                auth = FirebaseAuth.getInstance()
                firestore = FirebaseFirestore.getInstance()
                isFirebaseAvailable = true
                Log.d(tag, "Firebase initialized successfully.")
            }
        } catch (e: Exception) {
            Log.w(tag, "Firebase initialization warning: ${e.message}. Using offline/local storage fallback.")
            isFirebaseAvailable = false
        }
    }

    fun isAvailable(): Boolean = isFirebaseAvailable

    fun getCurrentUserEmail(): String? {
        return try {
            auth?.currentUser?.email
        } catch (e: Exception) {
            null
        }
    }

    suspend fun syncProfileToFirestore(profile: UserProfile): Boolean {
        if (!isFirebaseAvailable || firestore == null) return false
        return try {
            val data = hashMapOf(
                "uid" to profile.uid,
                "fullName" to profile.fullName,
                "email" to profile.email,
                "mobileNumber" to profile.mobileNumber,
                "selectedExam" to profile.selectedExam.name,
                "targetYear" to profile.targetYear,
                "academicLevel" to profile.academicLevel,
                "stateDomicile" to profile.stateDomicile,
                "studyStreakDays" to profile.studyStreakDays,
                "averageScore" to profile.averageScore,
                "totalQuestionsAttempted" to profile.totalQuestionsAttempted,
                "testsCompleted" to profile.testsCompleted,
                "overallAccuracy" to profile.overallAccuracy,
                "allIndiaRankPercentile" to profile.allIndiaRankPercentile,
                "updatedAt" to System.currentTimeMillis()
            )
            firestore?.collection("users")?.document(profile.uid)?.set(data)?.await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to sync profile to Firestore", e)
            false
        }
    }

    suspend fun syncTestAttemptToFirestore(attempt: TestAttemptResult, userUid: String): Boolean {
        if (!isFirebaseAvailable || firestore == null) return false
        return try {
            val data = hashMapOf(
                "attemptId" to attempt.id,
                "userUid" to userUid,
                "testTitle" to attempt.testTitle,
                "examType" to attempt.examType.name,
                "score" to attempt.score,
                "maxScore" to attempt.maxScore,
                "accuracyPercentage" to attempt.accuracyPercentage,
                "airRankPrediction" to attempt.airRankPrediction,
                "percentile" to attempt.percentile,
                "attemptedQuestions" to attempt.attemptedQuestions,
                "totalQuestions" to attempt.totalQuestions,
                "correctCount" to attempt.correctCount,
                "incorrectCount" to attempt.incorrectCount,
                "unattemptedCount" to attempt.unattemptedCount,
                "timeTakenSeconds" to attempt.timeTakenSeconds,
                "physicsScore" to attempt.physicsScore,
                "chemistryScore" to attempt.chemistryScore,
                "biologyScore" to attempt.biologyScore,
                "timestamp" to attempt.timestamp
            )
            firestore?.collection("test_attempts")?.document(attempt.id)?.set(data)?.await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to sync test attempt to Firestore", e)
            false
        }
    }

    suspend fun signInWithEmail(email: String, pass: String): Boolean {
        if (!isFirebaseAvailable || auth == null) return true // Offline mock success
        return try {
            auth?.signInWithEmailAndPassword(email, pass)?.await()
            true
        } catch (e: Exception) {
            Log.w(tag, "Sign in failed or using local profile: ${e.message}")
            true
        }
    }

    suspend fun registerWithEmail(email: String, pass: String): Boolean {
        if (!isFirebaseAvailable || auth == null) return true // Offline mock success
        return try {
            auth?.createUserWithEmailAndPassword(email, pass)?.await()
            true
        } catch (e: Exception) {
            Log.w(tag, "Create user failed or using local profile: ${e.message}")
            true
        }
    }
}
