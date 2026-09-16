package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun PasswordRecoveryScreen(
    onBackToLogin: () -> Unit,
    onRecoverySuccess: () -> Unit
) {
    var recoveryMethod by remember { mutableStateOf("mobile") } // "mobile" or "email"
    var mobileOrEmail by remember { mutableStateOf("98765 43210") }
    val otpDigits = remember { mutableStateListOf("4", "8", "2", "", "", "") }
    var focusedOtpIndex by remember { mutableIntStateOf(3) }
    var timerSeconds by remember { mutableIntStateOf(43) }

    LaunchedEffect(Unit) {
        while (timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        }
    }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Password Recovery",
                onBack = onBackToLogin,
                showProfile = true
            )
        },
        containerColor = ZenithBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Visual Security Hero
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.padding(bottom = 12.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(ZenithPrimaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Security Shield",
                            tint = ZenithPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(ZenithSecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Verified",
                            tint = ZenithOnSecondaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = "Reset Your Password",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ZenithOnSurface
                    )
                )

                Text(
                    text = "Don't worry, exam prep won't wait. Enter your registered mobile or email to receive a recovery code.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ZenithOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 6.dp, start = 20.dp, end = 20.dp)
                )
            }

            // Mode Selector Segmented Control
            Surface(
                color = ZenithSurfaceContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = {
                            recoveryMethod = "mobile"
                            mobileOrEmail = "98765 43210"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("tab_recovery_mobile"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (recoveryMethod == "mobile") ZenithSurfaceCard else Color.Transparent,
                            contentColor = if (recoveryMethod == "mobile") ZenithPrimary else ZenithOnSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = if (recoveryMethod == "mobile") ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Smartphone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text("Mobile OTP", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    Button(
                        onClick = {
                            recoveryMethod = "email"
                            mobileOrEmail = "ananya.sharma@zenithprep.edu"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("tab_recovery_email"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (recoveryMethod == "email") ZenithSurfaceCard else Color.Transparent,
                            contentColor = if (recoveryMethod == "email") ZenithPrimary else ZenithOnSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = if (recoveryMethod == "email") ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Mail, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text("Registered Email", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }

            // Main Recovery Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Identifier Field
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (recoveryMethod == "mobile") "REGISTERED MOBILE NUMBER" else "REGISTERED EMAIL ADDRESS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithOnSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )

                        OutlinedTextField(
                            value = mobileOrEmail,
                            onValueChange = { mobileOrEmail = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("recovery_identifier_input"),
                            leadingIcon = if (recoveryMethod == "mobile") {
                                {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                                    ) {
                                        Text("+91", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        VerticalDivider(modifier = Modifier.height(18.dp), color = ZenithSurfaceContainerHigh)
                                    }
                                }
                            } else null,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = StatusAnswered,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = ZenithSurfaceCard,
                                unfocusedContainerColor = ZenithSurfaceSubtle,
                                focusedBorderColor = BorderActive,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MarkChatRead,
                                    contentDescription = null,
                                    tint = ZenithSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (recoveryMethod == "mobile") "A 6-digit OTP will be sent to +91 98*** **210" else "A code will be sent to an***@zenithprep.edu",
                                    style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                                )
                            }
                            Text(
                                text = "Change",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithPrimary,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.clickable { /* allow edit */ }
                            )
                        }
                    }

                    // OTP Verification Grid
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ENTER 6-DIGIT CODE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithOnSurface,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )

                            Surface(
                                color = ZenithSecondaryContainer.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Sensors,
                                        contentDescription = null,
                                        tint = ZenithSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Auto-read enabled",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        // 6 Square Input Slots
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            otpDigits.forEachIndexed { index, digit ->
                                val isFocused = index == focusedOtpIndex
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isFocused) ZenithSurfaceCard else ZenithSurfaceSubtle)
                                        .border(
                                            width = if (isFocused) 2.dp else 1.dp,
                                            color = if (isFocused) BorderActive else BorderSubtle,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            focusedOtpIndex = index
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = digit,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            color = ZenithOnSurface
                                        )
                                    )
                                }
                            }
                        }

                        // Timer Strip
                        Surface(
                            color = ZenithSurfaceSubtle,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = ZenithOnSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Resend OTP in",
                                        style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                                    )
                                    Text(
                                        text = "00:${String.format("%02d", timerSeconds)}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = ZenithPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    )
                                }

                                Text(
                                    text = "Resend Now",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (timerSeconds == 0) ZenithPrimary else ZenithOnSurfaceVariant.copy(alpha = 0.5f),
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.clickable(enabled = timerSeconds == 0) {
                                        timerSeconds = 45
                                    }
                                )
                            }
                        }
                    }

                    // Action Button
                    Button(
                        onClick = onRecoverySuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("verify_otp_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Verify OTP & Set New Password",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Student Support & Guidance Helpline Banner
            Surface(
                color = StatusReviewBg,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ZenithTertiaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = null,
                                tint = StatusReview,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Trouble receiving OTP?",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )
                            Text(
                                text = "WhatsApp Student Helpline (+91 8000-ZENITH)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ZenithOnSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }

                    Surface(
                        color = ZenithSurfaceCard,
                        shape = RoundedCornerShape(8.dp),
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable { /* Open WhatsApp chat */ }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Chat",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = StatusReview,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.OpenInNew,
                                contentDescription = null,
                                tint = StatusReview,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Return to Login
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remember your password?",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ZenithOnSurfaceVariant)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Back to Login",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = ZenithPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable { onBackToLogin() }
                        .testTag("back_to_login_link")
                )
            }
        }
    }
}
