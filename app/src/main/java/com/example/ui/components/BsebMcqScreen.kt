package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.data.bseb.BsebMcq
import com.example.data.bseb.BsebPaper
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainUiState
import com.example.ui.viewmodel.MainViewModel

@Composable
fun BsebMcqScreen(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("bseb_mcq_screen_container")
    ) {
        // Hero Banner Card
        BsebHeroCard(
            engineState = uiState.bsebEngineState,
            onSimulateRollover = { viewModel.triggerAnnualRollover() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Class Selector
        ClassSelectorBar(
            selectedClass = uiState.bsebSelectedClass,
            onClassSelected = { viewModel.setBsebClass(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subject Selector
        SubjectSelectorBar(
            availableSubjects = uiState.bsebAvailableSubjects,
            selectedSubject = uiState.bsebSelectedSubject,
            onSubjectSelected = { viewModel.setBsebSubject(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 10-Year Selector
        TenYearSelectorBar(
            activeYears = uiState.bsebActiveYears,
            selectedYear = uiState.bsebSelectedYear,
            onYearSelected = { viewModel.setBsebYear(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // View Mode Switcher: "test" (Online Test) | "pdf" (PDF Paper View) | "engine" (10-Year Rolling Engine)
        BsebModeSelector(
            currentMode = uiState.bsebMode,
            onModeSelected = { viewModel.setBsebMode(it) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Rollover Notification Banner if rollover happened
        uiState.bsebRolloverResult?.let { result ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                border = androidx.compose.foundation.BorderStroke(1.dp, TricolorGreen.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = TricolorGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "स्वतः परीक्षा रोलओवर सफल (10-Year Window Updated)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF065F46)
                        )
                        Text(
                            text = result.message,
                            fontSize = 11.sp,
                            color = Color(0xFF047857)
                        )
                        Text(
                            text = "पूर्व विंडो: ${result.previousWindow} ➔ नवीन 10-वर्षीय विंडो: ${result.newWindow}",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BiharBlue
                        )
                    }
                }
            }
        }

        // Active Content according to bsebMode
        when (uiState.bsebMode) {
            "test" -> {
                BsebOnlineTestView(
                    paper = uiState.bsebActivePaper,
                    answers = uiState.bsebAnswers,
                    isSubmitted = uiState.bsebIsSubmitted,
                    score = uiState.bsebScore,
                    onOptionSelected = { qNo, optIdx -> viewModel.selectBsebOption(qNo, optIdx) },
                    onSubmit = { viewModel.submitBsebTest() },
                    onReset = { viewModel.resetBsebTest() }
                )
            }
            "pdf" -> {
                BsebPdfQuestionPaperView(
                    paper = uiState.bsebActivePaper,
                    onStartTest = { viewModel.setBsebMode("test") }
                )
            }
            "engine" -> {
                BsebRollingEngineInfoView(
                    engineState = uiState.bsebEngineState,
                    onSimulateRollover = { viewModel.triggerAnnualRollover() }
                )
            }
        }
    }
}

@Composable
private fun BsebHeroCard(
    engineState: com.example.data.bseb.ExamRollingEngineState?,
    onSimulateRollover: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BiharNavyDark),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = TricolorSaffron
                    ) {
                        Text(
                            text = "BSEB 10-YEARS",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "बिहार विद्यालय परीक्षा समिति (BSEB)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "विंडो: ${engineState?.currentWindowStartYear ?: 2016}–${engineState?.currentWindowEndYear ?: 2025}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "वर्षवार पूछे गए सभी 100% MCQ प्रश्न संग्रह (PDF व CBT टेस्ट)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "ऑटोमैटिक 10-वर्षीय रोलिंग सिस्टम: अगले वर्ष की परीक्षा होते ही नया वर्ष जुड़ेगा और 10वां वर्ष स्वतः डिलीट हो जाएगा।",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        text = "सक्रिय कुल पेपर: ${engineState?.totalActivePapersCount ?: 40} विषयवार",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }

                Button(
                    onClick = onSimulateRollover,
                    colors = ButtonDefaults.buttonColors(containerColor = BiharSaffron),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("simulate_exam_rollover_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Autorenew,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "वार्षिक अपडेट सिमुलेट करें",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ClassSelectorBar(
    selectedClass: Int,
    onClassSelected: (Int) -> Unit
) {
    val classes = listOf(10, 12, 9, 11)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        classes.forEach { cls ->
            val isSelected = cls == selectedClass
            val title = when (cls) {
                10 -> "कक्षा 10वीं (मैट्रिक)"
                12 -> "कक्षा 12वीं (इंटर)"
                9 -> "कक्षा 9वीं"
                11 -> "कक्षा 11वीं"
                else -> "कक्षा $cls"
            }

            Surface(
                onClick = { onClassSelected(cls) },
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) BiharBlue else Color.White,
                contentColor = if (isSelected) Color.White else BiharTextMid,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) BiharBlue else BiharBorder),
                modifier = Modifier
                    .weight(1f)
                    .testTag("class_tab_$cls")
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun SubjectSelectorBar(
    availableSubjects: List<String>,
    selectedSubject: String,
    onSubjectSelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "विषय:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = BiharTextDark,
            modifier = Modifier.padding(end = 2.dp)
        )

        availableSubjects.forEach { subj ->
            val isSelected = subj == selectedSubject
            Surface(
                onClick = { onSubjectSelected(subj) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) BiharSky else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) BiharBlue else BiharBorder
                ),
                modifier = Modifier.testTag("subject_chip_$subj")
            ) {
                Text(
                    text = subj,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) BiharBlue else BiharTextDark,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun TenYearSelectorBar(
    activeYears: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "10-वर्षीय परीक्षा वर्ष (BSEB Previous Year Exam Years):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BiharTextDark
            )
            Surface(
                color = Color(0xFFEFF6FF),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "चयनित: $selectedYear",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            activeYears.forEachIndexed { index, year ->
                val isSelected = year == selectedYear
                val isNewest = index == 0
                val isOldest = index == activeYears.size - 1

                Surface(
                    onClick = { onYearSelected(year) },
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) BiharBlue else if (isNewest) Color(0xFFFEF3C7) else Color.White,
                    contentColor = if (isSelected) Color.White else BiharTextDark,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) BiharBlue else if (isNewest) TricolorSaffron else BiharBorder
                    ),
                    modifier = Modifier.testTag("year_chip_$year")
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$year",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (isNewest) {
                            Text(
                                text = "नवीनतम",
                                fontSize = 8.5.sp,
                                color = if (isSelected) Color.White else Color(0xFFB45309)
                            )
                        } else if (isOldest) {
                            Text(
                                text = "10वां वर्ष",
                                fontSize = 8.5.sp,
                                color = if (isSelected) Color.White else BiharTextLight
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BsebModeSelector(
    currentMode: String,
    onModeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val modes = listOf(
            Triple("test", "ऑनलाइन टेस्ट (CBT)", Icons.Default.Quiz),
            Triple("pdf", "PDF प्रश्न पत्र दृश्य", Icons.Default.PictureAsPdf),
            Triple("engine", "10-वर्षीय ऑटो रोलिंग इंजन", Icons.Default.SettingsBackupRestore)
        )

        modes.forEach { (modeKey, title, icon) ->
            val isSelected = currentMode == modeKey
            Surface(
                onClick = { onModeSelected(modeKey) },
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) BiharSky else Color.White,
                contentColor = if (isSelected) BiharBlue else BiharTextMid,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) BiharBlue else BiharBorder
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("mode_tab_$modeKey")
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = if (isSelected) BiharBlue else BiharTextLight
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = title,
                        fontSize = 10.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 1. Online CBT Mock Test View
 */
@Composable
private fun BsebOnlineTestView(
    paper: BsebPaper?,
    answers: Map<Int, Int>,
    isSubmitted: Boolean,
    score: Int,
    onOptionSelected: (Int, Int) -> Unit,
    onSubmit: () -> Unit,
    onReset: () -> Unit
) {
    if (paper == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BiharBlue)
        }
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Paper Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                    Column {
                        Text(
                            text = "BSEB कक्षा ${paper.classGrade}वीं • ${paper.subject} (${paper.year})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "विषय कोड: ${paper.subjectCode} • कुल प्रश्न: ${paper.questions.size} • कुल अंक: ${paper.questions.size}",
                            fontSize = 11.sp,
                            color = BiharTextLight
                        )
                    }

                    Surface(
                        color = Color(0xFFFEF3C7),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "समय: 60 मिनट",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Answered counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "हल किए गए प्रश्न: ${answers.size} / ${paper.questions.size}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (answers.size == paper.questions.size) TricolorGreen else BiharBlue
                    )

                    if (!isSubmitted) {
                        Button(
                            onClick = onSubmit,
                            colors = ButtonDefaults.buttonColors(containerColor = TricolorGreen),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("submit_bseb_test_button")
                        ) {
                            Text("टेस्ट जमा करें (Submit Test)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        OutlinedButton(
                            onClick = onReset,
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("reset_bseb_test_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("पुनः टेस्ट दें", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Result Score Card if submitted
        if (isSubmitted) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, TricolorGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎉 परीक्षा परिणाम / Score Card",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF166534)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$score / ${paper.questions.size}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TricolorGreen
                    )
                    val percentage = if (paper.questions.isNotEmpty()) (score * 100) / paper.questions.size else 0
                    Text(
                        text = "सफलता प्रतिशत: $percentage% • श्रेणी: ${if (percentage >= 60) "प्रथम श्रेणी (1st Division)" else if (percentage >= 45) "द्वितीय श्रेणी (2nd Division)" else "उत्तीर्ण"}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF15803D)
                    )
                    Text(
                        text = "नीचे सभी प्रश्नों के सही उत्तर एवं व्याख्या देखें:",
                        fontSize = 11.sp,
                        color = BiharTextLight,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Questions List
        paper.questions.forEachIndexed { index, q ->
            McqQuestionItem(
                question = q,
                selectedOption = answers[q.qNo],
                isSubmitted = isSubmitted,
                onOptionSelect = { optIdx -> onOptionSelected(q.qNo, optIdx) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Bottom Submit or Reset Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!isSubmitted) {
                Button(
                    onClick = onSubmit,
                    colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(46.dp)
                        .testTag("bottom_submit_test_button")
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("पूरा टेस्ट सबमिट करें एवं परिणाम देखें", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onReset,
                    colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("नया टेस्ट प्रारंभ करें (Start New Test)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun McqQuestionItem(
    question: BsebMcq,
    selectedOption: Int?,
    isSubmitted: Boolean,
    onOptionSelect: (Int) -> Unit
) {
    val optionLabels = listOf("A", "B", "C", "D")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("mcq_card_${question.qNo}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSubmitted && selectedOption != null) {
                if (selectedOption == question.correctIndex) TricolorGreen else Color(0xFFEF4444)
            } else if (selectedOption != null) {
                BiharBlue
            } else {
                BiharBorder
            }
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Question Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = BiharSky,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Q${question.qNo}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharBlue
                            )
                        }
                    }

                    Column {
                        Text(
                            text = question.questionHindi,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BiharTextDark,
                            lineHeight = 19.sp
                        )
                        question.questionEnglish?.let { eng ->
                            Text(
                                text = eng,
                                fontSize = 11.5.sp,
                                color = BiharTextLight,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = question.chapterOrTopic,
                        fontSize = 9.sp,
                        color = BiharTextMid,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 4 Options
            question.options.forEachIndexed { optIdx, optText ->
                val isSelected = selectedOption == optIdx
                val isCorrectAnswer = optIdx == question.correctIndex

                val optionBg = when {
                    isSubmitted && isCorrectAnswer -> Color(0xFFDCFCE7) // Light Green
                    isSubmitted && isSelected && !isCorrectAnswer -> Color(0xFFFEE2E2) // Light Red
                    isSelected -> BiharSky
                    else -> Color(0xFFF8FAFC)
                }

                val optionBorder = when {
                    isSubmitted && isCorrectAnswer -> TricolorGreen
                    isSubmitted && isSelected && !isCorrectAnswer -> Color(0xFFEF4444)
                    isSelected -> BiharBlue
                    else -> Color(0xFFE2E8F0)
                }

                Surface(
                    onClick = { if (!isSubmitted) onOptionSelect(optIdx) },
                    shape = RoundedCornerShape(8.dp),
                    color = optionBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, optionBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .testTag("q_${question.qNo}_opt_$optIdx")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) BiharBlue else Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) BiharBlue else BiharBorder),
                            modifier = Modifier.size(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = optionLabels[optIdx],
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else BiharTextMid
                                )
                            }
                        }

                        Text(
                            text = optText,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected || (isSubmitted && isCorrectAnswer)) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSubmitted && isCorrectAnswer) Color(0xFF166534) else BiharTextDark,
                            modifier = Modifier.weight(1f)
                        )

                        if (isSubmitted) {
                            if (isCorrectAnswer) {
                                Text(
                                    text = "✓ सही उत्तर",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TricolorGreen
                                )
                            } else if (isSelected) {
                                Text(
                                    text = "✗ आपका उत्तर",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFDC2626)
                                )
                            }
                        }
                    }
                }
            }

            // Explanation Section if Submitted
            if (isSubmitted) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = BiharBlue,
                            modifier = Modifier.size(15.dp)
                        )
                        Column {
                            Text(
                                text = "स्पष्टीकरण (Explanation):",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharBlue
                            )
                            Text(
                                text = question.explanation,
                                fontSize = 11.sp,
                                color = BiharTextDark,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2. Complete PDF Question Paper View
 */
@Composable
private fun BsebPdfQuestionPaperView(
    paper: BsebPaper?,
    onStartTest: () -> Unit
) {
    if (paper == null) return

    Column(modifier = Modifier.fillMaxWidth()) {
        // PDF Simulation Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Official BSEB Exam Header
                Text(
                    text = "BIHAR SCHOOL EXAMINATION BOARD, PATNA",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BiharNavyDark,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "वार्षिक माध्यमिक / उच्च माध्यमिक परीक्षा (Annual Examination)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue
                )
                Text(
                    text = "कक्षा ${paper.classGrade}वीं • विषय: ${paper.subject} (कोड: ${paper.subjectCode}) • वर्ष: ${paper.year}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BiharTextDark,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Divider(
                    color = BiharBorder,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "कुल समय: 1 घंटा 15 मिनट", fontSize = 10.5.sp, color = BiharTextMid)
                    Text(text = "पूर्णांक: ${paper.questions.size}", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = BiharTextDark)
                }

                Surface(
                    color = Color(0xFFFEF3C7),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "निर्देश: परीक्षार्थी यथासंभव अपने शब्दों में ही उत्तर दें। प्रत्येक प्रश्न के साथ 4 विकल्प दिए गए हैं, जिनमें से केवल एक ही सही है। OMR शीट पर सही वृत्त को नीले/काले बॉलपेन से भरें।",
                        fontSize = 10.sp,
                        color = Color(0xFF92400E),
                        modifier = Modifier.padding(8.dp),
                        lineHeight = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onStartTest,
                        colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("इस पेपर का लाइव टेस्ट दें", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { /* Simulated PDF download */ },
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("PDF डाउनलोड करें", fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PDF Questions List with Answers
        paper.questions.forEach { q ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "प्र. ${q.qNo}. ${q.questionHindi}",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BiharTextDark,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "[1 अंक]",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharBlue
                        )
                    }

                    q.questionEnglish?.let { eng ->
                        Text(
                            text = "Q${q.qNo}. $eng",
                            fontSize = 11.sp,
                            color = BiharTextLight,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Grid of 4 options
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "(A) ${q.options.getOrElse(0) { "" }}", fontSize = 11.5.sp, color = BiharTextDark)
                            Text(text = "(C) ${q.options.getOrElse(2) { "" }}", fontSize = 11.5.sp, color = BiharTextDark)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "(B) ${q.options.getOrElse(1) { "" }}", fontSize = 11.5.sp, color = BiharTextDark)
                            Text(text = "(D) ${q.options.getOrElse(3) { "" }}", fontSize = 11.5.sp, color = BiharTextDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Correct answer badge + explanation
                    Surface(
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "उत्तर: (${listOf("A", "B", "C", "D")[q.correctIndex]}) ${q.options[q.correctIndex]}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF065F46)
                                )
                                Text(
                                    text = "• अध्याय: ${q.chapterOrTopic}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF047857)
                                )
                            }
                            Text(
                                text = "व्याख्या: ${q.explanation}",
                                fontSize = 10.5.sp,
                                color = Color(0xFF065F46),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 3. 10-Year Rolling Engine & Auto-Update System View
 */
@Composable
private fun BsebRollingEngineInfoView(
    engineState: com.example.data.bseb.ExamRollingEngineState?,
    onSimulateRollover: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = BiharBlue,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Column {
                    Text(
                        text = "BSEB 10-वर्षीय स्वचालित रोलिंग अपडेट इंजन",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharNavyDark
                    )
                    Text(
                        text = "10-Year Sliding Window Auto-Rollover Engine",
                        fontSize = 10.5.sp,
                        color = BiharTextLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation cards
            Surface(
                color = BiharSky,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "ऑटोमैटिक अपडेट नियम (System Rule):",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharBlue
                    )
                    Text(
                        text = "1. जैसे ही बिहार बोर्ड की अगले वर्ष की परीक्षा पूरी होगी (उदा. सत्र 2026), सभी कक्षाओं एवं विषयों के नए MCQ प्रश्न स्वचालित रूप से डेटाबेस में शामिल हो जाएँगे।\n2. सिस्टम की क्षमता ठीक 10 वर्ष की है, अतः सबसे पुराना 10वां वर्ष (उदा. 2016) स्वचालित रूप से सुरक्षित रूप से हटा दिया जाएगा।\n3. इससे विद्यार्थियों को हमेशा नवीनतम 10 वर्षों का सटीक एवं अद्यतन क्वेश्चन बैंक उपलब्ध रहेगा।",
                        fontSize = 11.sp,
                        color = BiharTextDark,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stats grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "वर्तमान विंडो", fontSize = 10.sp, color = BiharTextLight)
                        Text(
                            text = "${engineState?.currentWindowStartYear ?: 2016}–${engineState?.currentWindowEndYear ?: 2025}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharBlue
                        )
                        Text(text = "ठीक 10 वर्ष सक्रिय", fontSize = 9.sp, color = TricolorGreen)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "अगला रोलओवर वर्ष", fontSize = 10.sp, color = BiharTextLight)
                        Text(
                            text = "${engineState?.nextScheduledRollYear ?: 2026}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TricolorSaffron
                        )
                        Text(text = "स्वचालित शेड्यूल्ड", fontSize = 9.sp, color = BiharTextMid)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "सक्रिय पेपर्स", fontSize = 10.sp, color = BiharTextLight)
                        Text(
                            text = "${engineState?.totalActivePapersCount ?: 40}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharNavyDark
                        )
                        Text(text = "100% MCQ कवर्ड", fontSize = 9.sp, color = TricolorGreen)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action button
            Button(
                onClick = onSimulateRollover,
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("engine_simulate_rollover_button")
            ) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = TricolorSaffron)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "वार्षिक परीक्षा रोलओवर चलाएँ (अगला वर्ष जोड़ें व 10वां वर्ष हटाएं)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
