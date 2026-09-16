package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ExamRepository
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object PasswordRecovery : Screen("recovery")
    object Home : Screen("home")
    object TestInstructions : Screen("instructions")
    object Exam : Screen("exam")
    object Result : Screen("result")
    object Syllabus : Screen("syllabus")
    object Analytics : Screen("analytics")
    object Bookmarks : Screen("bookmarks")
    object Profile : Screen("profile")
    object AdminPanel : Screen("admin")
}

class ExamViewModel(application: Application) : AndroidViewModel(application) {
    val repository = ExamRepository(application)

    val userProfile = repository.userProfile
    val questions = repository.questions
    val bookmarks = repository.bookmarks
    val selectedAnswers = repository.selectedAnswers
    val markedForReview = repository.markedForReview
    val visitedQuestions = repository.visitedQuestions
    val latestResult = repository.latestResult
    val securityViolations = repository.securityViolations

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Login)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Test countdown timer in seconds (3h 20m = 200m = 12,000s; starts at 2h 45m 16s = 9916s for the exact screen state)
    private val _remainingSeconds = MutableStateFlow(9916L)
    val remainingSeconds: StateFlow<Long> = _remainingSeconds.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(24)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedSection = MutableStateFlow("Physics (Sec A)")
    val selectedSection: StateFlow<String> = _selectedSection.asStateFlow()

    private val _showSubmitDialog = MutableStateFlow(false)
    val showSubmitDialog: StateFlow<Boolean> = _showSubmitDialog.asStateFlow()

    private val _showPaletteDrawer = MutableStateFlow(false)
    val showPaletteDrawer: StateFlow<Boolean> = _showPaletteDrawer.asStateFlow()

    private val _testLanguage = MutableStateFlow("en") // "en" or "hi"
    val testLanguage: StateFlow<String> = _testLanguage.asStateFlow()

    private var timerJob: Job? = null

    init {
        // Initial setup
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        if (screen == Screen.Exam) {
            startExamTimer()
        }
    }

    fun startExamTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(1000)
                _remainingSeconds.value = _remainingSeconds.value - 1
                if (_remainingSeconds.value == 0L) {
                    submitExamAuto()
                }
            }
        }
    }

    fun selectQuestion(number: Int) {
        _currentQuestionIndex.value = number
        repository.markVisited(number)
    }

    fun selectSection(sectionName: String) {
        _selectedSection.value = sectionName
    }

    fun selectOption(optionIndex: Int) {
        repository.selectAnswer(_currentQuestionIndex.value, optionIndex)
    }

    fun clearOption() {
        repository.clearAnswer(_currentQuestionIndex.value)
    }

    fun toggleMarkReview() {
        repository.toggleMarkReview(_currentQuestionIndex.value)
    }

    fun saveAndNext() {
        val next = (_currentQuestionIndex.value + 1).coerceAtMost(200)
        _currentQuestionIndex.value = next
        repository.markVisited(next)
    }

    fun previousQuestion() {
        val prev = (_currentQuestionIndex.value - 1).coerceAtLeast(1)
        _currentQuestionIndex.value = prev
        repository.markVisited(prev)
    }

    fun openPaletteDrawer() {
        _showPaletteDrawer.value = true
    }

    fun closePaletteDrawer() {
        _showPaletteDrawer.value = false
    }

    fun setTestLanguage(lang: String) {
        _testLanguage.value = lang
    }

    fun openSubmitDialog() {
        _showSubmitDialog.value = true
    }

    fun closeSubmitDialog() {
        _showSubmitDialog.value = false
    }

    fun confirmSubmitExam() {
        _showSubmitDialog.value = false
        timerJob?.cancel()
        val timeSpent = 12000L - _remainingSeconds.value
        repository.submitExam(timeTakenSeconds = timeSpent)
        _currentScreen.value = Screen.Result
    }

    private fun submitExamAuto() {
        timerJob?.cancel()
        repository.logSecurityEvent("Auto-submitted at zero clock")
        repository.submitExam()
        _currentScreen.value = Screen.Result
    }

    fun handleAppBackgrounded() {
        if (_currentScreen.value == Screen.Exam) {
            repository.logSecurityEvent("Window focus loss / tab switch detected")
        }
    }
}
