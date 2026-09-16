package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.ExamViewModel
import com.example.ui.Screen
import com.example.ui.screens.*
import com.example.ui.theme.ZenithTheme

class MainActivity : ComponentActivity() {
    private val examViewModel: ExamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenithTheme {
                ZenithApp(viewModel = examViewModel)
            }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        examViewModel.handleAppBackgrounded()
    }
}

@Composable
fun ZenithApp(viewModel: ExamViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val latestResult by viewModel.latestResult.collectAsState()
    val allQuestions by viewModel.questions.collectAsState()
    val bookmarkedQuestions = allQuestions.filter { it.isBookmarked }
    val securityLogs by viewModel.securityViolations.collectAsState()

    AnimatedContent(
        targetState = currentScreen,
        label = "ScreenTransition",
        modifier = Modifier.fillMaxSize()
    ) { screen ->
        when (screen) {
            is Screen.Login -> {
                LoginScreen(
                    onLoginSuccess = { viewModel.navigateTo(Screen.Home) },
                    onNavigateRegister = { viewModel.navigateTo(Screen.Register) },
                    onNavigateForgotPassword = { viewModel.navigateTo(Screen.PasswordRecovery) },
                    onStartGrandMock = { viewModel.navigateTo(Screen.TestInstructions) }
                )
            }
            is Screen.Register -> {
                RegisterScreen(
                    onRegisterSuccess = { profile ->
                        viewModel.repository.updateProfile(profile)
                        viewModel.navigateTo(Screen.Home)
                    },
                    onNavigateLogin = { viewModel.navigateTo(Screen.Login) }
                )
            }
            is Screen.PasswordRecovery -> {
                PasswordRecoveryScreen(
                    onBackToLogin = { viewModel.navigateTo(Screen.Login) },
                    onRecoverySuccess = { viewModel.navigateTo(Screen.Login) }
                )
            }
            is Screen.Home -> {
                HomeScreen(
                    userProfile = userProfile,
                    onStartTest = { viewModel.navigateTo(Screen.TestInstructions) },
                    onViewResult = { viewModel.navigateTo(Screen.Result) },
                    onNavigateSyllabus = { viewModel.navigateTo(Screen.Syllabus) },
                    onNavigateAnalytics = { viewModel.navigateTo(Screen.Analytics) },
                    onNavigateBookmarks = { viewModel.navigateTo(Screen.Bookmarks) },
                    onNavigateProfile = { viewModel.navigateTo(Screen.Profile) },
                    onSwitchExam = { exam -> viewModel.repository.setExamType(exam) }
                )
            }
            is Screen.TestInstructions -> {
                TestInstructionsScreen(
                    onBack = { viewModel.navigateTo(Screen.Home) },
                    onStartExam = { viewModel.navigateTo(Screen.Exam) }
                )
            }
            is Screen.Exam -> {
                ExamScreen(
                    viewModel = viewModel,
                    onSubmitExam = { viewModel.navigateTo(Screen.Result) }
                )
            }
            is Screen.Result -> {
                ResultScreen(
                    result = latestResult,
                    onReturnHome = { viewModel.navigateTo(Screen.Home) },
                    onReviewSolutions = {
                        viewModel.selectQuestion(24)
                        viewModel.navigateTo(Screen.Exam)
                    }
                )
            }
            is Screen.Syllabus -> {
                SyllabusScreen(
                    onBack = { viewModel.navigateTo(Screen.Home) },
                    onStartChapterDrill = { chapterName ->
                        viewModel.selectQuestion(1)
                        viewModel.navigateTo(Screen.Exam)
                    }
                )
            }
            is Screen.Analytics -> {
                AnalyticsScreen(
                    userProfile = userProfile,
                    onBack = { viewModel.navigateTo(Screen.Home) },
                    onStartRecommendedDrill = { chapterName ->
                        viewModel.selectQuestion(1)
                        viewModel.navigateTo(Screen.Exam)
                    }
                )
            }
            is Screen.Bookmarks -> {
                BookmarksScreen(
                    bookmarkedQuestions = bookmarkedQuestions,
                    onBack = { viewModel.navigateTo(Screen.Home) },
                    onToggleBookmark = { q -> viewModel.repository.toggleBookmark(q) },
                    onPracticeQuestion = { q ->
                        viewModel.selectQuestion(q.questionNumber)
                        viewModel.navigateTo(Screen.Exam)
                    }
                )
            }
            is Screen.Profile -> {
                ProfileScreen(
                    userProfile = userProfile,
                    onBack = { viewModel.navigateTo(Screen.Home) },
                    onSwitchExam = { exam -> viewModel.repository.setExamType(exam) },
                    onOpenAdminPanel = { viewModel.navigateTo(Screen.AdminPanel) },
                    onSignOut = { viewModel.navigateTo(Screen.Login) }
                )
            }
            is Screen.AdminPanel -> {
                AdminPanelScreen(
                    securityLogs = securityLogs,
                    onBack = { viewModel.navigateTo(Screen.Profile) },
                    onAddQuestion = { newQ -> viewModel.repository.addQuestionAdmin(newQ) }
                )
            }
        }
    }
}
