package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM questions WHERE examType = :examType ORDER BY questionNumber ASC")
    fun getQuestionsForExam(examType: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE subject = :subject ORDER BY questionNumber ASC")
    fun getQuestionsBySubject(subject: String): Flow<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Query("UPDATE questions SET isBookmarked = :isBookmarked WHERE id = :questionId")
    suspend fun updateBookmarkStatus(questionId: String, isBookmarked: Boolean)

    @Query("SELECT * FROM test_attempts ORDER BY timestamp DESC")
    fun getAllTestAttempts(): Flow<List<TestAttemptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestAttempt(attempt: TestAttemptEntity)

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE questionId = :questionId")
    suspend fun removeBookmark(questionId: String)
}

@Database(
    entities = [QuestionEntity::class, TestAttemptEntity::class, BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDao
}
