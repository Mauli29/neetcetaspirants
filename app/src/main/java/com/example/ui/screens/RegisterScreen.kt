package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ExamType
import com.example.model.UserProfile
import com.example.ui.components.ZenithTopBar
import com.example.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterSuccess: (UserProfile) -> Unit,
    onNavigateLogin: () -> Unit
) {
    var selectedTrack by remember { mutableStateOf(ExamType.NEET) }
    var selectedYear by remember { mutableStateOf("2025") }
    var fullName by remember { mutableStateOf("Ananya Sharma") }
    var mobileNumber by remember { mutableStateOf("9876543210") }
    var email by remember { mutableStateOf("ananya.sharma@example.com") }
    var stateDomicile by remember { mutableStateOf("Maharashtra (MH-CET / State Quota)") }
    var password by remember { mutableStateOf("NeetRank1@2025") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAgreed by remember { mutableStateOf(true) }

    val states = listOf(
        "Maharashtra (MH-CET / State Quota)",
        "Karnataka (KCET / State Quota)",
        "Delhi NCR (DU / IPU Quota)",
        "Tamil Nadu (TNEA / State Quota)",
        "Uttar Pradesh (UP NEET Quota)",
        "Rajasthan (RUHS Quota)",
        "Other State / All India Quota"
    )
    var stateDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ZenithTopBar(
                title = "Create Account",
                onBack = onNavigateLogin,
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
            // Header & Brand Presentation
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.padding(bottom = 8.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ZenithSurfaceContainerLow)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Zenith Academic Logo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(ZenithSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Text(
                    text = "Start Your Prep Journey",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ZenithPrimary
                    )
                )

                Text(
                    text = "Join 100,000+ medical and engineering aspirants nationwide",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ZenithOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Live Aspirant Activity Pill
                Surface(
                    color = StatusAnsweredBg,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(StatusAnswered)
                        )
                        Text(
                            text = "3,420 Active Mock Tests Today",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = StatusAnswered,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Main Registration Card Form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_form_card"),
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
                    // Target Exam Track
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Target Exam Track",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )
                            Surface(
                                color = ZenithPrimaryFixed,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Syllabus Tailored",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Options grid: NEET vs CET/JEE
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // NEET UG
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedTrack = ExamType.NEET }
                                    .testTag("track_neet"),
                                color = if (selectedTrack == ExamType.NEET) ZenithPrimaryFixed else ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(10.dp),
                                border = if (selectedTrack == ExamType.NEET) BorderStroke(1.5.dp, ZenithPrimary) else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (selectedTrack == ExamType.NEET) ZenithPrimary else ZenithSurfaceContainerHighest),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MedicalServices,
                                            contentDescription = null,
                                            tint = if (selectedTrack == ExamType.NEET) Color.White else ZenithPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "NEET UG",
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                            )
                                            if (selectedTrack == ExamType.NEET) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = ZenithPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "MBBS / BDS Track",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ZenithOnSurfaceVariant,
                                                fontSize = 10.sp
                                            ),
                                            maxLines = 1
                                        )
                                    }
                                }
                            }

                            // State CET / JEE
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedTrack = ExamType.JEE_MAIN }
                                    .testTag("track_jee"),
                                color = if (selectedTrack != ExamType.NEET) ZenithPrimaryFixed else ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(10.dp),
                                border = if (selectedTrack != ExamType.NEET) BorderStroke(1.5.dp, ZenithPrimary) else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (selectedTrack != ExamType.NEET) ZenithPrimary else ZenithSurfaceContainerHighest),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Science,
                                            contentDescription = null,
                                            tint = if (selectedTrack != ExamType.NEET) Color.White else ZenithPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "CET / JEE",
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                            )
                                            if (selectedTrack != ExamType.NEET) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = ZenithPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "B.Tech / Pharmacy",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ZenithOnSurfaceVariant,
                                                fontSize = 10.sp
                                            ),
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Target Exam Year Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Target Exam Year",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ZenithOnSurface
                                )
                            )
                            Text(
                                text = "Sets calibration timeline",
                                style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                "2025" to "Dropper / 12th",
                                "2026" to "11th Standard",
                                "2027" to "Foundation"
                            ).forEach { (year, desc) ->
                                val isSelected = selectedYear == year
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedYear = year }
                                        .testTag("year_chip_$year"),
                                    color = if (isSelected) ZenithPrimaryContainer else ZenithSurfaceSubtle,
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = year,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else ZenithOnSurfaceVariant,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        )
                                        Text(
                                            text = desc,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (isSelected) Color.White.copy(alpha = 0.9f) else ZenithOnSurfaceVariant,
                                                fontSize = 9.sp
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }

                    HorizontalDivider(color = ZenithSurfaceContainerHigh)

                    // Candidate Full Name
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Candidate Full Name *",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("name_input"),
                            leadingIcon = {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = ZenithOnSurfaceVariant)
                            },
                            placeholder = { Text("e.g. Ananya Sharma") },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = ZenithSurfaceCard,
                                unfocusedContainerColor = ZenithSurfaceSubtle,
                                focusedBorderColor = BorderActive,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }

                    // Mobile Number
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Mobile Number *",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "For OTP & AIR alerts",
                                style = MaterialTheme.typography.labelSmall.copy(color = ZenithOnSurfaceVariant)
                            )
                        }

                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { mobileNumber = it.filter { ch -> ch.isDigit() }.take(10) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_mobile_input"),
                            leadingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                                ) {
                                    Text("🇮🇳 +91", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    VerticalDivider(modifier = Modifier.height(18.dp), color = ZenithSurfaceContainerHigh)
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
                    }

                    // Email Address
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Email Address (Optional for scorecards)",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_email_input"),
                            leadingIcon = {
                                Icon(Icons.Default.Mail, contentDescription = null, tint = ZenithOnSurfaceVariant)
                            },
                            placeholder = { Text("ananya.sharma@example.com") },
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

                    // State Domicile Dropdown
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "State Domicile *",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "85% State Quota Accuracy",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Box {
                            OutlinedTextField(
                                value = stateDomicile,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { stateDropdownExpanded = true }
                                    .testTag("state_domicile_selector"),
                                leadingIcon = {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = ZenithOnSurfaceVariant)
                                },
                                trailingIcon = {
                                    IconButton(onClick = { stateDropdownExpanded = true }) {
                                        Icon(Icons.Default.ExpandMore, contentDescription = "Dropdown")
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = ZenithSurfaceSubtle,
                                    unfocusedContainerColor = ZenithSurfaceSubtle,
                                    focusedBorderColor = BorderActive,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )

                            DropdownMenu(
                                expanded = stateDropdownExpanded,
                                onDismissRequest = { stateDropdownExpanded = false }
                            ) {
                                states.forEach { stateName ->
                                    DropdownMenuItem(
                                        text = { Text(stateName) },
                                        onClick = {
                                            stateDomicile = stateName
                                            stateDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Set Password with dynamic strength meter
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Set Password *",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "Strong",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ZenithSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_password_input"),
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ZenithOnSurfaceVariant)
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle"
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

                        // 4-Bar Strength Meter
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(ZenithSecondary))
                            Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(ZenithSecondary))
                            Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(ZenithSecondary))
                            Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(ZenithSurfaceContainerHighest))
                        }
                        Text(
                            text = "Use uppercase, numbers & special characters.",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ZenithOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }

                    // T&C Checkbox
                    Surface(
                        color = ZenithSurfaceContainerLow,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { termsAgreed = !termsAgreed },
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = termsAgreed,
                                onCheckedChange = { termsAgreed = it },
                                colors = CheckboxDefaults.colors(checkedColor = ZenithPrimary),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "I agree to the Zenith Terms of Service, Privacy Policy, and consent to receive exam pattern updates via WhatsApp/SMS.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ZenithOnSurface,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                        }
                    }

                    // Submit CTA Button with Live Calibration Badge
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = ZenithTertiaryFixed,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = ZenithOnTertiaryFixed,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Instant AIR Rank Calibration Included",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ZenithOnTertiaryFixed,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val profile = UserProfile(
                                    fullName = fullName,
                                    mobileNumber = mobileNumber,
                                    email = email,
                                    selectedExam = selectedTrack,
                                    targetYear = selectedYear,
                                    stateDomicile = stateDomicile
                                )
                                onRegisterSuccess(profile)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("create_account_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ZenithPrimaryContainer)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Create Account & Get Free Mock Test",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    // Trust Indicator Strip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple(Icons.Default.Shield, "NTA Compliant", ZenithSecondary),
                            Triple(Icons.Default.Timer, "Real CBT Timer", ZenithPrimary),
                            Triple(Icons.Default.Psychology, "AI Mistake Book", StatusReview)
                        ).forEach { (icon, title, color) ->
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = ZenithSurfaceSubtle,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ZenithOnSurfaceVariant,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 10.sp
                                        ),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium.copy(color = ZenithOnSurfaceVariant)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = ZenithPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable { onNavigateLogin() }
                        .testTag("signin_link")
                )
            }
        }
    }
}
