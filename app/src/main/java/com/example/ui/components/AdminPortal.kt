package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockDataRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainUiState
import com.example.ui.viewmodel.MainViewModel

@Composable
fun AdminPortal(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    if (!uiState.isAdminLoggedIn) {
        AdminLoginCard(viewModel = viewModel)
    } else {
        AdminDashboard(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun AdminLoginCard(viewModel: MainViewModel) {
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
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin Login",
                        tint = BiharBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "प्रशासक / प्रधानाध्यापक पोर्टल",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "UDISE+ 10301901805 अधिकृत प्रशासनिक लॉगिन",
                fontSize = 12.sp,
                color = BiharTextMid,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Button(
                onClick = { viewModel.loginAdmin() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("admin_login_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "प्रशासक लॉगिन करें (Login)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Demo Login Card
            Button(
                onClick = { viewModel.fillDemoLogin(com.example.data.AppRole.ADMIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("demo_admin_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharSky),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder)
            ) {
                Text(
                    text = "त्वरित डेमो लॉगिन (Dr. R.K. Pandey - Principal)",
                    fontSize = 12.sp,
                    color = BiharBlue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AdminDashboard(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Admin Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(BiharBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "डॉ. रामेश्वर कुमार पांडेय",
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "प्रधानाध्यापक (Headmaster / DDO)",
                            fontSize = 11.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "UDISE+: 10301901805 • सिमरी (बक्सर)",
                            fontSize = 9.5.sp,
                            color = BiharTextLight
                        )
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.logoutCurrentRole() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("admin_logout_btn")
                ) {
                    Text("लॉगआउट", fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Admin Sub-tabs
        val subTabs = listOf(
            "dashboard" to "विद्यालय सारांश",
            "bseb_engine" to "BSEB 10-वर्षीय रोलिंग इंजन",
            "students" to "नामांकित विद्यार्थी सूची",
            "staff" to "शिक्षक एवं कर्मचारी",
            "sync" to "e-ShikshaKosh सिंक",
            "circulars" to "सरकारी अधिसूचनाएं"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            subTabs.forEach { (tabKey, tabTitle) ->
                val isSelected = uiState.adminSubTab == tabKey
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setAdminSubTab(tabKey) },
                    label = { Text(tabTitle, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("admin_subtab_$tabKey")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState.adminSubTab) {
            "dashboard" -> AdminOverviewStats(viewModel = viewModel)
            "bseb_engine" -> AdminBsebEngineSection(uiState = uiState, viewModel = viewModel)
            "students" -> AdminStudentsDirectory(uiState = uiState, viewModel = viewModel)
            "staff" -> AdminStaffDirectory(uiState = uiState)
            "sync" -> AdminSyncSection(viewModel = viewModel)
            "circulars" -> AdminCircularsSection(uiState = uiState)
            else -> AdminOverviewStats(viewModel = viewModel)
        }
    }
}

@Composable
fun AdminOverviewStats(viewModel: MainViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "कुल नामांकित छात्र",
                value = "842 विद्यार्थी",
                subtitle = "कक्षा 9-12 (सत्र 2025-26)",
                icon = Icons.Default.Groups,
                accentColor = BiharBlue,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "आज की औसत उपस्थिति",
                value = "91.4%",
                subtitle = "769 छात्र उपस्थित",
                icon = Icons.Default.HowToReg,
                accentColor = TricolorGreen,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "शिक्षक एवं कर्मचारी",
                value = "18 शिक्षक",
                subtitle = "सभी बायोमेट्रिक उपस्थित",
                icon = Icons.Default.WorkOutline,
                accentColor = TricolorSaffron,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "पुस्तकालय संग्रह",
                value = "4,250 पुस्तकें",
                subtitle = "e-Granthalaya पंजीकृत",
                icon = Icons.Default.LocalLibrary,
                accentColor = BiharBlue,
                modifier = Modifier.weight(1f)
            )
        }

        // Quick Admin Actions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "प्रशासनिक त्वरित कार्य (Administrative Actions)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharTextDark
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.syncWithEShikshakosh() },
                        colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("e-ShikshaKosh सिंक", fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = { viewModel.setAdminSubTab("students") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.List, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("विद्यार्थी रजिस्टर", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStudentsDirectory(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    var searchQuery = uiState.adminSearchQuery
    var selectedClass = uiState.adminClassFilter

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Search & Class filter
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setAdminSearchQuery(it) },
            placeholder = { Text("नाम, रोल नंबर अथवा UDISE से खोजें...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedClass == null,
                onClick = { viewModel.setAdminClassFilter(null) },
                label = { Text("सभी कक्षाएं", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = BiharBlue,
                    selectedLabelColor = Color.White
                )
            )
            listOf(9, 10, 11, 12).forEach { cls ->
                FilterChip(
                    selected = selectedClass == cls,
                    onClick = { viewModel.setAdminClassFilter(cls) },
                    label = { Text("कक्षा $cls", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        val filteredStudents = uiState.students.filter { student ->
            (selectedClass == null || student.studentClass == selectedClass) &&
                    (searchQuery.isBlank() ||
                            student.name.contains(searchQuery, ignoreCase = true) ||
                            student.rollNo.toString().contains(searchQuery) ||
                            student.udiseStudentId.contains(searchQuery))
        }

        Text(
            text = "कुल परिणाम: ${filteredStudents.size} विद्यार्थी",
            fontSize = 11.sp,
            color = BiharTextLight,
            fontWeight = FontWeight.SemiBold
        )

        filteredStudents.forEach { student ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = student.name,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextDark
                            )
                            Surface(
                                color = BiharSky,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "कक्षा ${student.studentClass} (${student.stream})",
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BiharBlue,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "रोल: ${student.rollNo} • पिता: ${student.fatherName} • मो: ${student.mobile}",
                            fontSize = 10.5.sp,
                            color = BiharTextMid,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "छात्रवृत्ति: ${student.scholarshipStatus}",
                            fontSize = 10.sp,
                            color = GreenText,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${student.attendancePercentage}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (student.attendancePercentage >= 75) TricolorGreen else Color.Red
                        )
                        Text(text = "उपस्थिति", fontSize = 9.sp, color = BiharTextLight)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStaffDirectory(uiState: MainUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "उच्च माध्यमिक विद्यालय, गंगौली - शिक्षक एवं कर्मचारी रोस्टर",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = BiharBlue
        )

        uiState.teachers.forEach { teacher ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BiharSky),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = BiharBlue)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = teacher.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = teacher.designation,
                            fontSize = 11.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "योग्यता: ${teacher.qualification} • संपर्क: ${teacher.mobile}",
                            fontSize = 9.5.sp,
                            color = BiharTextLight
                        )
                    }

                    Surface(
                        color = GreenSurface,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "बायोमेट्रिक पंच",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = GreenText,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSyncSection(viewModel: MainViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CloudSync,
                contentDescription = null,
                tint = BiharBlue,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "e-ShikshaKosh एवं UDISE+ डाटा सिंक्रोनाइज़ेशन",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue,
                textAlign = TextAlign.Center
            )
            Text(
                text = "विद्यालय कोड: 10301901805 • बिहार शिक्षा विभाग सेंट्रल सर्वर",
                fontSize = 11.sp,
                color = BiharTextLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )

            Button(
                onClick = { viewModel.syncWithEShikshakosh() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("अभी सिंक्रोनाइज़ करें (Sync Now)", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun AdminCircularsSection(uiState: MainUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "बिहार शिक्षा परियोजना परिषद एवं BSEB परिपत्र",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = BiharBlue
        )

        uiState.notices.forEach { notice ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = if (notice.isUrgent) RedSurface else BiharSky,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = notice.category,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (notice.isUrgent) Color.Red else BiharBlue,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                        Text(text = notice.date, fontSize = 9.5.sp, color = BiharTextLight)
                    }
                    Text(
                        text = notice.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = BiharTextDark,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AdminBsebEngineSection(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    val engineState = uiState.bsebEngineState

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BiharNavyDark),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "बिहार बोर्ड 10-वर्षीय परीक्षा प्रश्न पत्र स्वतः अपडेट सिस्टम",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Surface(
                        color = TricolorGreen,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "सिस्टम सक्रिय",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "वार्षिक परीक्षा संपन्न होते ही नए वर्ष के सभी विषयवार MCQ प्रश्न स्वतः जुड़ते हैं एवं सबसे पुराना 10वां वर्ष स्वतः सिस्टम से सुरक्षित रूप से हट जाता है।",
                    fontSize = 11.5.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "वर्तमान 10-वर्षीय विंडो", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                            Text(
                                text = "${engineState?.currentWindowStartYear ?: 2016}–${engineState?.currentWindowEndYear ?: 2025}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "आगामी परीक्षा वर्ष", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                            Text(
                                text = "${engineState?.nextScheduledRollYear ?: 2026}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TricolorSaffron
                            )
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "कुल सक्रिय पेपर्स", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                            Text(
                                text = "${engineState?.totalActivePapersCount ?: 40}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6EE7B7)
                            )
                        }
                    }
                }
            }
        }

        // Rollover Simulation Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "रोलओवर सिमुलेशन एवं डेटाबेस प्रबंधन",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharTextDark
                )
                Text(
                    text = "वार्षिक परीक्षा परिणाम के बाद स्वतः होने वाले अपडेट को तुरंत टेस्ट करने हेतु नीचे दिए बटन पर क्लिक करें:",
                    fontSize = 11.sp,
                    color = BiharTextMid,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.triggerAnnualRollover() },
                        colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("admin_simulate_rollover_btn")
                    ) {
                        Icon(Icons.Default.Autorenew, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("वार्षिक रोलओवर सिमुलेट करें", fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = { viewModel.setActiveRole(com.example.data.AppRole.BSEB_MCQ) },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("MCQ बैंक में देखें", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
