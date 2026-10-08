package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.MockDataRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainUiState
import com.example.ui.viewmodel.MainViewModel

@Composable
fun StudentPortal(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    if (!uiState.isStudentLoggedIn) {
        StudentLoginCard(uiState = uiState, viewModel = viewModel)
    } else {
        StudentDashboard(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun StudentLoginCard(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = BiharSky,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Student Login",
                        tint = BiharBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "छात्र लॉगिन (Student Portal)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "अपने पंजीकृत मोबाइल नंबर अथवा रोल नंबर से लॉगिन करें",
                fontSize = 12.sp,
                color = BiharTextMid,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            OutlinedTextField(
                value = uiState.loginMobile,
                onValueChange = { viewModel.updateLoginMobile(it) },
                label = { Text("मोबाइल नंबर (Username)") },
                placeholder = { Text("उदा. 9876543210") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("student_mobile_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.loginPassword,
                onValueChange = { viewModel.updateLoginPassword(it) },
                label = { Text("पासवर्ड (Password)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("student_password_input"),
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.rememberMe,
                    onCheckedChange = { viewModel.toggleRememberMe(it) }
                )
                Text(
                    text = "मुझे याद रखें (Remember Me)",
                    fontSize = 12.sp,
                    color = BiharTextDark
                )
            }

            Button(
                onClick = { viewModel.loginStudent() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("student_login_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "लॉगिन करें (Login)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(10.dp))

            // Two Outlined Buttons: छात्र पंजीकरण and शिक्षक पंजीकरण
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.showMessage("नया छात्र पंजीकरण: विद्यालय कार्यालय से संपर्क करें या e-ShikshaKosh पर जाएं") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BiharBlue),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BiharBlue)
                ) {
                    Text("छात्र पंजीकरण", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = { viewModel.setActiveRole(com.example.data.AppRole.TEACHER) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BiharBlue),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BiharBlue)
                ) {
                    Text("शिक्षक लॉगिन", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Demo Login Shortcut
            Card(
                colors = CardDefaults.cardColors(containerColor = BiharSky),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "त्वरित डेमो लॉगिन (Demo Login):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { viewModel.fillDemoLogin(com.example.data.AppRole.STUDENT) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("demo_student_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBlue)
                    ) {
                        Text(
                            text = "Amit Kumar (कक्षा 10, रोल 14) • 9876543210",
                            fontSize = 12.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Links: नामांकित विद्यार्थी सूची, विद्यालय परिचय, पुस्तकालय (Library)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { viewModel.setActiveRole(com.example.data.AppRole.ADMIN) }) {
                    Text("नामांकित विद्यार्थी सूची", fontSize = 11.sp, color = BiharBlue, fontWeight = FontWeight.SemiBold)
                }
                Text("•", color = BiharTextLight, fontSize = 10.sp)
                TextButton(onClick = { viewModel.setActiveRole(com.example.data.AppRole.ABOUT) }) {
                    Text("विद्यालय परिचय", fontSize = 11.sp, color = BiharBlue, fontWeight = FontWeight.SemiBold)
                }
                Text("•", color = BiharTextLight, fontSize = 10.sp)
                TextButton(onClick = { viewModel.setActiveRole(com.example.data.AppRole.LIBRARY) }) {
                    Text("पुस्तकालय (Library)", fontSize = 11.sp, color = BiharBlue, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun StudentDashboard(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    val student = uiState.currentStudent

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Student Info Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(BiharBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = student.name.take(2),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Column {
                        Text(
                            text = student.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "कक्षा: ${student.studentClass}वीं '${student.section}' | क्रमांक: ${student.rollNo}",
                            fontSize = 12.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "UDISE: ${student.udiseStudentId}",
                            fontSize = 10.sp,
                            color = BiharTextLight
                        )
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.logoutCurrentRole() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("student_logout_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("लॉगआउट", fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Student Sub-tabs
        val subTabs = listOf(
            "overview" to "डैशबोर्ड",
            "id_card" to "डिजिटल पहचान पत्र",
            "attendance" to "उपस्थिति",
            "marks" to "परीक्षा परिणाम",
            "timetable" to "समय सारणी",
            "library_card" to "पुस्तकालय कार्ड",
            "scholarship" to "छात्रवृत्ति स्थिति"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            subTabs.forEach { (tabKey, tabTitle) ->
                val isSelected = uiState.studentSubTab == tabKey
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setStudentSubTab(tabKey) },
                    label = { Text(tabTitle, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("student_subtab_$tabKey")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (uiState.studentSubTab) {
            "overview" -> StudentOverviewSection(student = student, uiState = uiState, viewModel = viewModel)
            "id_card" -> StudentDigitalIdCard(student = student)
            "attendance" -> StudentAttendanceSection(student = student)
            "marks" -> StudentMarksSection()
            "timetable" -> StudentTimetableSection()
            "library_card" -> StudentLibraryCardSection(student = student, uiState = uiState)
            "scholarship" -> StudentScholarshipSection(student = student)
            else -> StudentOverviewSection(student = student, uiState = uiState, viewModel = viewModel)
        }
    }
}

@Composable
fun StudentOverviewSection(
    student: com.example.data.Student,
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Quick Stat Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "वार्षिक उपस्थिति",
                value = "${student.attendancePercentage}%",
                subtitle = "e-Shikshakosh सत्यापित",
                icon = Icons.Default.CheckCircle,
                accentColor = TricolorGreen,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "जारी पुस्तकें",
                value = "1 पुस्तक",
                subtitle = "पुस्तकालय कोटा: 2",
                icon = Icons.Default.Book,
                accentColor = BiharBlue,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "अर्धवार्षिक प्राप्तांक",
                value = "87.8%",
                subtitle = "श्रेणी: A1 (प्रथम)",
                icon = Icons.Default.Grade,
                accentColor = TricolorSaffron,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "छात्रवृत्ति (DBT)",
                value = "₹10,000",
                subtitle = "खाते में अंतरित",
                icon = Icons.Default.AccountBalance,
                accentColor = GreenText,
                modifier = Modifier.weight(1f)
            )
        }

        // Quick Actions Row
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "त्वरित सेवाएं (Quick Services)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharTextDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    QuickActionButton(
                        icon = Icons.Default.Badge,
                        label = "पहचान पत्र",
                        onClick = { viewModel.setStudentSubTab("id_card") }
                    )
                    QuickActionButton(
                        icon = Icons.Default.CalendarToday,
                        label = "समय सारणी",
                        onClick = { viewModel.setStudentSubTab("timetable") }
                    )
                    QuickActionButton(
                        icon = Icons.Default.MenuBook,
                        label = "पुस्तकालय",
                        onClick = { viewModel.setStudentSubTab("library_card") }
                    )
                    QuickActionButton(
                        icon = Icons.Default.Assessment,
                        label = "मार्कशीट",
                        onClick = { viewModel.setStudentSubTab("marks") }
                    )
                }
            }
        }

        // BSEB 10-Year MCQ Question Bank & Mock Test Feature Banner
        Surface(
            onClick = { viewModel.setActiveRole(com.example.data.AppRole.BSEB_MCQ) },
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF1E3A8A),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TricolorSaffron),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("student_bseb_mcq_banner")
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = TricolorSaffron,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "बिहार बोर्ड 10-वर्षीय MCQ बैंक",
                                color = Color.White,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = TricolorGreen
                            ) {
                                Text(
                                    text = "100% पूछे गए प्रश्न",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "सभी कक्षाओं व विषयों के वर्षवार MCQ • PDF व ऑनलाइन CBT टेस्ट • स्वतः वार्षिक अपडेट",
                            color = Color(0xFFE2E8F0),
                            fontSize = 10.5.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = TricolorSaffron
                ) {
                    Text(
                        text = "टेस्ट दें →",
                        color = Color.White,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Live Web Portal Banner
        Surface(
            onClick = { viewModel.setActiveRole(com.example.data.AppRole.WEB) },
            shape = RoundedCornerShape(12.dp),
            color = BiharBlue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🌐", fontSize = 18.sp)
                        }
                    }
                    Column {
                        Text(
                            text = "लाइव वेबसाइट पोर्टल दृश्य (Live Web Portal)",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "e-ShikshaKosh स्टाइल फुल वेबसाइट व्यू में खोलें",
                            color = Color(0xFFBAE6FD),
                            fontSize = 10.5.sp
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = TricolorSaffron
                ) {
                    Text(
                        text = "खोलें →",
                        color = Color(0xFF0F172A),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = BiharTextMid,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = value,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = BiharTextDark,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
                text = subtitle,
                fontSize = 9.5.sp,
                color = accentColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            shape = CircleShape,
            color = BiharSky,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = BiharBlue,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = BiharTextDark,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StudentDigitalIdCard(student: com.example.data.Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("digital_id_card"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, BiharBlue)
    ) {
        Column {
            // ID Card Top Govt Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BiharBlue)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "बिहार सरकार • शिक्षा विभाग",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "उच्च माध्यमिक विद्यालय, गंगौली (बक्सर)",
                        color = BiharSky,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "छात्र डिजिटल पहचान पत्र (Session 2025-2026)",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 9.sp
                    )
                }
            }

            // Tricolor Strip
            TricolorBorder()

            // Student Photo & Details Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Photo Placeholder
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BiharSky)
                            .border(1.dp, BiharBlue, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Photo",
                            tint = BiharBlue,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "रोल: ${student.rollNo}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                }

                // Details
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = student.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharTextDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    IdDetailItem("कक्षा एवं वर्ग", "कक्षा ${student.studentClass}वीं - ${student.section}")
                    IdDetailItem("पिता का नाम", student.fatherName)
                    IdDetailItem("माता का नाम", student.motherName)
                    IdDetailItem("जन्म तिथि", student.dob)
                    IdDetailItem("रक्त समूह", student.bloodGroup)
                    IdDetailItem("मोबाइल", student.mobile)
                }
            }

            Divider(color = BiharBorder)

            // QR Code Box & Signature
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Simulated QR Code
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .background(Color.White)
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = "QR Code",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Black
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "UDISE: ${student.udiseStudentId}",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = BiharTextDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "डॉ. आर. के. पांडेय",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                    Text(
                        text = "प्रधानाध्यापक के हस्ताक्षर",
                        fontSize = 9.sp,
                        color = BiharTextLight
                    )
                }
            }
        }
    }
}

@Composable
fun IdDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 1.dp)
    ) {
        Text(
            text = "$label: ",
            fontSize = 10.5.sp,
            color = BiharTextMid,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 10.5.sp,
            color = BiharTextDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun StudentAttendanceSection(student: com.example.data.Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "दैनिक उपस्थिति विवरण (e-ShikshaKosh)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "शैक्षणिक सत्र 2025-2026 में कुल कार्य दिवस: 154 दिन",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LinearProgressIndicator(
                progress = { student.attendancePercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = TricolorGreen,
                trackColor = BiharSky
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AttendanceBadge(label = "कुल कार्य दिवस", count = "154")
                AttendanceBadge(label = "उपस्थित दिन", count = "144", color = TricolorGreen)
                AttendanceBadge(label = "अनुपस्थित दिन", count = "10", color = Color.Red)
                AttendanceBadge(label = "प्रतिशत", count = "${student.attendancePercentage}%", color = BiharBlue)
            }

            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                color = GreenSurface,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GreenText)
                    Text(
                        text = "बिहार बोर्ड नियमानुसार 75% अनिवार्य उपस्थिति पूर्ण (पात्रता: परीक्षा फॉर्म एवं छात्रवृत्ति)",
                        fontSize = 11.sp,
                        color = GreenText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceBadge(label: String, count: String, color: Color = BiharTextDark) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = label, fontSize = 10.sp, color = BiharTextMid)
    }
}

@Composable
fun StudentMarksSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "त्रैमासिक / अर्धवार्षिक परीक्षा अंक विवरण",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "BSEB पटना परीक्षा पैटर्न आधारित मूल्यांकन",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            MockDataRepository.demoMarks.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = item.subject, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = BiharTextDark)
                        Text(text = "पूर्णांक: ${item.totalMarks}", fontSize = 10.sp, color = BiharTextLight)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${item.marksObtained} / ${item.totalMarks}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharBlue
                        )
                        Surface(
                            color = if (item.grade.startsWith("A")) GreenSurface else BiharSky,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = item.grade,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (item.grade.startsWith("A")) GreenText else BiharBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Divider(color = BiharBorder.copy(alpha = 0.6f))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BiharBackground)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "कुल प्राप्तांक: 535 / 600", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BiharBlue)
                Text(text = "प्रतिशत: 89.1% (A1)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GreenText)
            }
        }
    }
}

@Composable
fun StudentTimetableSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "कक्षा 10वीं 'A' - दैनिक समय सारणी",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "विद्यालय समय: प्रातः 09:00 बजे से अपराह्न 03:00 बजे तक",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            MockDataRepository.demoTimetable.forEach { period ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(BiharSky),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${period.periodNo}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = period.subject,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "${period.teacherName} • ${period.roomNo}",
                            fontSize = 10.sp,
                            color = BiharTextMid
                        )
                    }

                    Text(
                        text = period.timeSlot,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = BiharBlue
                    )
                }
                Divider(color = BiharBorder.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun StudentLibraryCardSection(
    student: com.example.data.Student,
    uiState: MainUiState
) {
    val issuedForAmit = uiState.issueRecords.filter { it.studentName == student.name && !it.isReturned }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ई-ग्रंथालय डिजिटल वाचन कार्ड",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                    Surface(
                        color = BiharSky,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "कोटा: ${issuedForAmit.size} / 2 पुस्तकें",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharBlue,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "बिहार सरकार सर्कुलर अनुसार कक्षा 9-10 हेतु अधिकतम 2 पुस्तकें अनुमन्य",
                    fontSize = 10.sp,
                    color = BiharTextLight,
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                )

                if (issuedForAmit.isEmpty()) {
                    Text(
                        text = "वर्तमान में कोई पुस्तक निर्गत नहीं है। पुस्तकालय से पुस्तक प्राप्त करें।",
                        fontSize = 12.sp,
                        color = BiharTextMid,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    issuedForAmit.forEach { record ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = BiharBackground),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = record.bookTitle,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BiharTextDark
                                )
                                Text(
                                    text = "परिग्रहण सं. (Acc No): ${record.accessionNo}",
                                    fontSize = 10.sp,
                                    color = BiharTextMid
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "निर्गमन: ${record.issueDate}",
                                        fontSize = 10.sp,
                                        color = BiharTextMid
                                    )
                                    Text(
                                        text = "जमा करने की तिथि: ${record.dueDate}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (record.fineAmount > 0) Color.Red else BiharBlue
                                    )
                                }
                                if (record.fineAmount > 0) {
                                    Text(
                                        text = "विलंब शुल्क (Fine): ₹${record.fineAmount} (बिहार सरकार नियमानुसार)",
                                        fontSize = 10.sp,
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentScholarshipSection(student: com.example.data.Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "बिहार राज्य छात्रवृत्ति एवं प्रोत्साहन योजनाएं",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = GreenSurface,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = student.scholarshipScheme,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenText
                    )
                    Text(
                        text = "स्थिति: ${student.scholarshipStatus}",
                        fontSize = 11.sp,
                        color = GreenText,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "अन्य संबद्ध योजनाएं:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BiharTextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            ScholarshipItem(title = "मुख्यमंत्री बालक साइकिल योजना", status = "स्वीकृत (₹3,000 अंतरित)", isDone = true)
            ScholarshipItem(title = "मुख्यमंत्री पोशाक योजना", status = "स्वीकृत (₹1,500 अंतरित)", isDone = true)
            ScholarshipItem(title = "मुफ्त पाठ्यपुस्तक वितरण योजना", status = "विद्यालय द्वारा पुस्तकें वितरित", isDone = true)
        }
    }
}

@Composable
fun ScholarshipItem(title: String, status: String, isDone: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Pending,
            contentDescription = null,
            tint = if (isDone) TricolorGreen else BiharTextLight,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = BiharTextDark)
            Text(text = status, fontSize = 9.5.sp, color = BiharTextMid)
        }
    }
}
