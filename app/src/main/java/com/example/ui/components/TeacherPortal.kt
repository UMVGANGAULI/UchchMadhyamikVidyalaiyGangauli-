package com.example.ui.components

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
import androidx.compose.runtime.*
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
fun TeacherPortal(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    if (!uiState.isTeacherLoggedIn) {
        TeacherLoginCard(uiState = uiState, viewModel = viewModel)
    } else {
        TeacherDashboard(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun TeacherLoginCard(
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
                        imageVector = Icons.Default.CoPresent,
                        contentDescription = "Teacher Login",
                        tint = BiharBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "शिक्षक लॉगिन (Teacher Portal)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "e-ShikshaKosh शिक्षक आईडी अथवा मोबाइल नंबर से लॉगिन करें",
                fontSize = 12.sp,
                color = BiharTextMid,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            OutlinedTextField(
                value = uiState.loginMobile,
                onValueChange = { viewModel.updateLoginMobile(it) },
                label = { Text("शिक्षक मोबाइल नंबर (Username)") },
                placeholder = { Text("उदा. 9876500010") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("teacher_mobile_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.loginPassword,
                onValueChange = { viewModel.updateLoginPassword(it) },
                label = { Text("पासवर्ड / बायोमेट्रिक पिन") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("teacher_password_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.loginTeacher() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("teacher_login_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "शिक्षक लॉगिन करें (Login)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Demo Login Card
            Card(
                colors = CardDefaults.cardColors(containerColor = BiharSky),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "त्वरित डेमो लॉगिन (Demo Teacher):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { viewModel.fillDemoLogin(com.example.data.AppRole.TEACHER) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("demo_teacher_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBlue)
                    ) {
                        Text(
                            text = "Mukesh (Math Teacher) • 9876500010",
                            fontSize = 12.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherDashboard(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    val teacher = uiState.currentTeacher

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Teacher Profile Header
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
                            imageVector = Icons.Default.CoPresent,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = teacher.name,
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = teacher.designation,
                            fontSize = 11.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(TricolorGreen)
                            )
                            Text(
                                text = "e-ShikshaKosh बायोमेट्रिक: ${teacher.biometricPunchTime}",
                                fontSize = 9.5.sp,
                                color = GreenText,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.logoutCurrentRole() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("teacher_logout_btn")
                ) {
                    Text("लॉगआउट", fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Teacher Sub-tabs
        val subTabs = listOf(
            "attendance" to "दैनिक हाजिरी (Roll Call)",
            "marks" to "अंक प्रविष्टि (Marks)",
            "notices" to "गृहकार्य व सूचनाएं",
            "leave" to "अवकाश आवेदन"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            subTabs.forEach { (tabKey, tabTitle) ->
                val isSelected = uiState.teacherSubTab == tabKey
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setTeacherSubTab(tabKey) },
                    label = { Text(tabTitle, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("teacher_subtab_$tabKey")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState.teacherSubTab) {
            "attendance" -> TeacherAttendanceSection(uiState = uiState, viewModel = viewModel)
            "marks" -> TeacherMarksEntrySection(uiState = uiState, viewModel = viewModel)
            "notices" -> TeacherHomeworkSection(viewModel = viewModel)
            "leave" -> TeacherLeaveSection(viewModel = viewModel)
            else -> TeacherAttendanceSection(uiState = uiState, viewModel = viewModel)
        }
    }
}

@Composable
fun TeacherAttendanceSection(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    val currentClassStudents = uiState.students.filter { it.studentClass == uiState.teacherAttendanceClass }
    val presentCount = currentClassStudents.count { uiState.attendanceRollCall[it.id] != false }
    val absentCount = currentClassStudents.size - presentCount

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Class Selector Row
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "कक्षा चुनें (Select Class for Roll Call):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharTextDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(9, 10, 11, 12).forEach { cls ->
                        val isSelected = uiState.teacherAttendanceClass == cls
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = { viewModel.changeTeacherAttendanceClass(cls) },
                            label = { Text("कक्षा ${cls}वीं", fontSize = 11.sp) },
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                selectedContainerColor = BiharBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Summary Bar & Mark All Present
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = GreenSurface,
                    shape = RoundedCornerShape(6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
                ) {
                    Text(
                        text = "उपस्थित: $presentCount",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = RedSurface,
                    shape = RoundedCornerShape(6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFCA5A5))
                ) {
                    Text(
                        text = "अनुपस्थित: $absentCount",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            OutlinedButton(
                onClick = { viewModel.markAllPresent() },
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("सभी उपस्थित", fontSize = 11.sp)
            }
        }

        // Students Roll Call List
        currentClassStudents.forEach { student ->
            val isPresent = uiState.attendanceRollCall[student.id] ?: true

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleStudentAttendance(student.id) }
                    .testTag("roll_call_item_${student.rollNo}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isPresent) Color.White else Color(0xFFFFF1F2)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isPresent) BiharBorder else Color(0xFFFDA4AF)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isPresent) BiharSky else RedSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${student.rollNo}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPresent) BiharBlue else Color.Red
                            )
                        }

                        Column {
                            Text(
                                text = student.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BiharTextDark
                            )
                            Text(
                                text = "पिता: ${student.fatherName}",
                                fontSize = 10.sp,
                                color = BiharTextLight
                            )
                        }
                    }

                    // Toggle Button
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isPresent) TricolorGreen else Color.Red,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = if (isPresent) "P (उपस्थित)" else "A (अनुपस्थित)",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Submit Attendance Button
        Button(
            onClick = { viewModel.submitDailyAttendance() },
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("submit_attendance_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "e-ShikshaKosh पर दैनिक हाजिरी सबमिट करें",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (uiState.attendanceSubmittedDate != null) {
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
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GreenText)
                    Text(
                        text = "कक्षा ${uiState.teacherAttendanceClass} की आज की उपस्थिति e-ShikshaKosh पोर्टल पर लॉक एवं अपलोड हो चुकी है।",
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
fun TeacherMarksEntrySection(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "विषय: गणित (Mathematics) - मूल्यांकन अंक प्रविष्टि",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "कक्षा 10वीं 'A' • मासिक मूल्यांकन (पूर्णांक: 50)",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            uiState.students.filter { it.studentClass == 10 }.forEach { student ->
                var marks by remember { mutableStateOf(if (student.rollNo == 14) "46" else "42") }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "रोल ${student.rollNo}: ${student.name}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "UDISE: ${student.udiseStudentId}",
                            fontSize = 9.5.sp,
                            color = BiharTextLight
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedTextField(
                            value = marks,
                            onValueChange = { marks = it },
                            modifier = Modifier.width(68.dp),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(text = "/ 50", fontSize = 11.sp, color = BiharTextMid)
                    }
                }
                Divider(color = BiharBorder.copy(alpha = 0.5f))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { viewModel.showMessage("गणित मासिक मूल्यांकन अंक सफलतापूर्वक सुरक्षित किए गए!") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("अंक सूची सुरक्षित करें (Save Marks)", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun TeacherHomeworkSection(viewModel: MainViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "कक्षा गृहकार्य एवं नोटिस जारी करें",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("शीर्षक (Title)") },
                placeholder = { Text("उदा. अध्याय 4: द्विघात समीकरण अभ्यास प्रश्न") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("विवरण (Details & Questions)") },
                placeholder = { Text("प्रश्नावली 4.2 के प्रश्न संख्या 1 से 6 तक हल करें।") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.showMessage("गृहकार्य कक्षा 10वीं के विद्यार्थियों को प्रेषित किया गया!")
                    title = ""
                    description = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("जारी करें (Publish Homework)", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun TeacherLeaveSection(viewModel: MainViewModel) {
    var leaveReason by remember { mutableStateOf("") }
    var leaveDays by remember { mutableStateOf("1") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "ऑनलाइन आकस्मिक अवकाश आवेदन (CL Request)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "बिहार सेवा संहिता नियमानुसार आकस्मिक अवकाश (शेष अवकाश: 11 दिन)",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = leaveDays,
                onValueChange = { leaveDays = it },
                label = { Text("दिनों की संख्या") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = leaveReason,
                onValueChange = { leaveReason = it },
                label = { Text("अवकाश का कारण") },
                placeholder = { Text("उदा. आवश्यक पारिवारिक कार्य") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.showMessage("अवकाश आवेदन प्रधानाध्यापक महोदय को अनुमोदन हेतु भेजा गया!")
                    leaveReason = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("आवेदन प्रस्तुत करें (Apply Leave)", fontSize = 12.sp)
            }
        }
    }
}
