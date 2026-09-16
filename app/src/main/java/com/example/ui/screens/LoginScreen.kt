package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateForgotPassword: () -> Unit,
    onStartGrandMock: () -> Unit
) {
    var authMode by remember { mutableStateOf("otp") } // "otp" or "password"
    var phoneNumber by remember { mutableStateOf("9876543210") }
    var email by remember { mutableStateOf("zenith.neet25@gmail.com") }
    var password by remember { mutableStateOf("NeetRank1@2025") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Student Login",
                onBack = null,
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
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero / Brand Welcome Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Emblem with NTA MOCK badge
                Box(
                    modifier = Modifier.padding(bottom = 8.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ZenithSurfaceContainerLow)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Zenith Emblem",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // NTA MOCK verified pill
                    Surface(
                        color = ZenithSecondary,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "NTA MOCK",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }

                // Target series pill
                Surface(
                    color = ZenithSurfaceContainerHigh,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ZenithSecondary)
                        )
                        Text(
                            text = "Target 2025 NEET & CET Series",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Text(
                    text = "Welcome Back, Future Doctor/Engineer!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ZenithOnSurface,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = "Sign in to access your national mocks, test series, and performance analytics.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ZenithOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 24.dp)
                )
            }

            // Main Auth Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_card"),
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
                    // Tab Switcher
                    Surface(
                        color = ZenithSurfaceSubtle,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Phone & OTP Tab
                            Button(
                                onClick = { authMode = "otp" },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .testTag("tab_phone_otp"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (authMode == "otp") ZenithSurfaceCard else Color.Transparent,
                                    contentColor = if (authMode == "otp") ZenithPrimary else ZenithOnSurfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp),
                                elevation = if (authMode == "otp") ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(0.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Smartphone,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Phone & OTP",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                }
                            }

                            // Email & Password Tab
                            Button(
                                onClick = { authMode = "password" },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .testTag("tab_email_password"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (authMode == "password") ZenithSurfaceCard else Color.Transparent,
                                    contentColor = if (authMode == "password") ZenithPrimary else ZenithOnSurfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp),
                                elevation = if (authMode == "password") ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(0.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mail,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Email & Pass",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                }
                            }
                        }
                    }

                    if (authMode == "otp") {
                        // Phone Number Input
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Registered Mobile Number",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ZenithOnSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            )

                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it.filter { ch -> ch.isDigit() }.take(10) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("phone_input"),
                                leadingIcon = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                                    ) {
                                        Text(text = "🇮🇳", fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "+91",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            tint = ZenithOnSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                },
                                trailingIcon = {
                                    if (phoneNumber.length == 10) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Valid",
                                            tint = StatusAnswered,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                },
                                placeholder = { Text("98765 43210") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "An SMS with 6-digit OTP will be sent",
                                    style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = ZenithSecondary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "Instant Delivery",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithSecondary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        // Remember Me Checkbox
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { rememberMe = !rememberMe }
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = ZenithPrimary)
                            )
                            Text(
                                text = "Remember me on this device",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }

                        // Send OTP Button
                        Button(
                            onClick = onLoginSuccess,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("send_otp_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Send OTP to Continue",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    } else {
                        // Email & Password Form
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Application Number / Registered Email",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ZenithOnSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("email_input"),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Badge,
                                        contentDescription = null,
                                        tint = ZenithOnSurfaceVariant
                                    )
                                },
                                placeholder = { Text("zenith.neet25@gmail.com") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = ZenithSurfaceCard,
                                    unfocusedContainerColor = ZenithSurfaceSubtle,
                                    focusedBorderColor = BorderActive,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Portal Password",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = ZenithOnSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Text(
                                    text = "Forgot Password?",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .clickable { onNavigateForgotPassword() }
                                        .testTag("forgot_password_link")
                                )
                            }

                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("password_input"),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = ZenithOnSurfaceVariant
                                    )
                                },
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = "Toggle Password"
                                        )
                                    }
                                },
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = ZenithSurfaceCard,
                                    unfocusedContainerColor = ZenithSurfaceSubtle,
                                    focusedBorderColor = BorderActive,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }

                        // Keep Me Logged In Checkbox
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { rememberMe = !rememberMe }
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = ZenithPrimary)
                            )
                            Text(
                                text = "Keep me logged in",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }

                        // Sign In Button
                        Button(
                            onClick = onLoginSuccess,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("signin_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Sign In to Portal",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    // Divider: or sign in with
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = ZenithSurfaceContainerHigh
                        )
                        Text(
                            text = "OR SIGN IN WITH",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithOnSurfaceVariant,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp
                            )
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = ZenithSurfaceContainerHigh
                        )
                    }

                    // Google Account Button
                    OutlinedButton(
                        onClick = onLoginSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("google_sign_in_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = ZenithSurfaceSubtle,
                            contentColor = ZenithOnSurface
                        ),
                        border = BorderStroke(1.dp, BorderSubtle)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Stylized G logo
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                modifier = Modifier.size(22.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "G",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF4285F4),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            Text(
                                text = "Google Account",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }
            }

            // Active Real-Time Mock Test Banner (Free Live Test)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStartGrandMock() }
                    .testTag("live_mock_banner"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ZenithSurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(StatusAnsweredBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = StatusAnswered,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "NEET All-India Grand Mock #14",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )
                            Text(
                                text = "Live window closing in 04h : 22m",
                                style = MaterialTheme.typography.bodySmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }
                    }

                    Surface(
                        color = ZenithSecondaryFixed,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "FREE",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithOnSecondaryFixedVariant,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }

            // Footer Links & Security Badge
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium.copy(color = ZenithOnSurfaceVariant)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Sign Up / Register",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = ZenithPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .clickable { onNavigateRegister() }
                            .testTag("signup_link")
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = ZenithSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "100% Secure NTA Pattern Mock Test Portal • 256-bit SSL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = StatusUnvisited,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}
