package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppRole
import com.example.data.Notice
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun GovtTopBar() {
    Surface(
        color = BiharNavyDark,
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "बिहार सरकार | GOVERNMENT OF BIHAR",
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.4.sp
                )
                Text(
                    text = "•",
                    fontSize = 10.5.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = "शिक्षा विभाग - e-ShikshaKosh",
                    fontSize = 10.5.sp,
                    color = BiharSky,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "सत्र: 2025-2026",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "UDISE: 10301901805",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TricolorBorder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(TricolorSaffron)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(TricolorWhite)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(TricolorGreen)
        )
    }
}

/**
 * Header Mockup Preview — matching the exact Meta AI mockup specification:
 * - Left: Bihar Government Education Logo + Text
 * - Beech (Center): उच्च माध्यमिक विद्यालय, गंगौली | सत्र: 2025-2026
 * - Right: School Logo (उ.मा.वि. गंगौली, स्थापना: 1972, circular UMV crest)
 */
@Composable
fun HeaderMockupPreview() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("header_mockup_preview_card"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Live Mockup Top Ribbon
            Surface(
                color = Color(0xFFF8FAFC),
                modifier = Modifier.fillMaxWidth(),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BiharBlue,
                            modifier = Modifier.size(18.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "✓",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "HEADER PREVIEW - जैसा दिखना चाहिए",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark,
                            letterSpacing = 0.3.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = BiharSky,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder)
                        ) {
                            Text(
                                text = "Live Mockup",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                            )
                        }
                    }

                    Text(
                        text = "Bihar Govt Style + Premium",
                        fontSize = 9.5.sp,
                        color = BiharTextLight,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 4px Tricolor Border
            TricolorBorder()

            // Header Content: Left (Bihar Govt Logo), Center (School Name & Session), Right (School Logo)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // LEFT (40%): Bihar Government Official State Logo & Title
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                            shadowElevation = 1.dp,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_bihar_emblem),
                                contentDescription = "शिक्षा विभाग बिहार सरकार लोगो",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "शिक्षा विभाग, बिहार सरकार",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharBlue,
                                lineHeight = 15.sp
                            )
                            Text(
                                text = "DEPT. OF EDUCATION, BIHAR",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextMid,
                                letterSpacing = 0.5.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                modifier = Modifier.padding(top = 1.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(14.dp)
                                        .height(1.5.dp)
                                        .background(TricolorSaffron)
                                )
                                Text(
                                    text = "सत्यमेव जयते",
                                    fontSize = 8.5.sp,
                                    color = BiharTextLight,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // RIGHT (20%): School Logo & Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "उ.मा.वि. गंगौली",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextDark
                            )
                            Text(
                                text = "स्थापना: 1972",
                                fontSize = 8.5.sp,
                                color = BiharTextLight
                            )
                        }

                        // UMV Round Seal
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(BiharBlue, BiharNavyDark)
                                    )
                                )
                                .border(2.dp, Color.White, CircleShape)
                                .border(3.dp, BiharBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "UMV",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // BEECH (CENTER): उच्च माध्यमिक विद्यालय, गंगौली | सत्र: 2025-2026
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BiharBackground),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "उच्च माध्यमिक विद्यालय, गंगौली",
                            fontSize = 16.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = BiharTextDark,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "प्रखंड: सिमरी | जिला: बक्सर (बिहार)",
                            fontSize = 11.sp,
                            color = BiharTextMid,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Surface(
                            color = BiharSky,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                text = "सत्र: 2025-2026 • कक्षा 9वीं, 10वीं, 11वीं एवं 12वीं छात्र पोर्टल",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Exactly 3 Primary Tabs requested by user:
 * 1. छात्र (Student) - Active by default
 * 2. शिक्षक (Teacher)
 * 3. प्रशासक (Admin)
 * with active status badge indicator
 */
@Composable
fun ThreeMainTabsBar(
    activeRole: AppRole,
    onRoleSelected: (AppRole) -> Unit
) {
    val threeRoles = listOf(
        AppRole.STUDENT,
        AppRole.TEACHER,
        AppRole.ADMIN
    )

    Surface(
        color = Color(0xFFF8FAFC),
        modifier = Modifier.fillMaxWidth(),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                threeRoles.forEach { role ->
                    val isSelected = role == activeRole
                    val icon = when (role) {
                        AppRole.STUDENT -> Icons.Default.School
                        AppRole.TEACHER -> Icons.Default.Person
                        AppRole.ADMIN -> Icons.Default.AdminPanelSettings
                        else -> Icons.Default.School
                    }

                    Surface(
                        onClick = { onRoleSelected(role) },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) BiharBlue else Color.White,
                        contentColor = if (isSelected) Color.White else BiharTextMid,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) BiharBlue else BiharBorder
                        ),
                        shadowElevation = if (isSelected) 2.dp else 0.dp,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("tab_${role.name.lowercase()}")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = role.labelHindi,
                                modifier = Modifier.size(15.dp),
                                tint = if (isSelected) Color.White else BiharBlue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${role.labelHindi} (${role.labelEnglish})",
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Active Tab Status indicator line
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(TricolorGreen)
                    )
                    Text(
                        text = when (activeRole) {
                            AppRole.STUDENT -> "छात्र पोर्टल सक्रिय (Student Portal Active)"
                            AppRole.TEACHER -> "शिक्षक पोर्टल सक्रिय (Teacher Portal Active)"
                            AppRole.ADMIN -> "प्रशासक पोर्टल सक्रिय (Admin Portal Active)"
                            AppRole.LIBRARY -> "पुस्तकालय मॉड्यूल सक्रिय (Library Module)"
                            AppRole.ABOUT -> "विद्यालय परिचय (About School)"
                            AppRole.WEB -> "लाइव वेबसाइट दृश्य (Live Web Portal)"
                            AppRole.BSEB_MCQ -> "बिहार बोर्ड 10-वर्षीय MCQ बैंक व टेस्ट इंजन"
                        },
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BiharBlue
                    )
                }

                Text(
                    text = "e-ShikshaKosh 2.0 Style",
                    fontSize = 9.5.sp,
                    color = BiharTextLight
                )
            }
        }
    }
}

/**
 * Quick Utility Links for Library, Enrolled Students list, and School Profile
 */
@Composable
fun QuickUtilityNavLinks(
    activeRole: AppRole,
    onRoleSelected: (AppRole) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            onClick = { onRoleSelected(AppRole.BSEB_MCQ) },
            shape = RoundedCornerShape(14.dp),
            color = if (activeRole == AppRole.BSEB_MCQ) BiharSky else Color(0xFFFEF3C7),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (activeRole == AppRole.BSEB_MCQ) BiharBlue else TricolorSaffron),
            modifier = Modifier.testTag("quick_link_bseb_mcq")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Quiz,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = if (activeRole == AppRole.BSEB_MCQ) BiharBlue else Color(0xFFB45309)
                )
                Text(
                    text = "10-वर्षीय बिहार बोर्ड MCQ बैंक (BSEB MCQs)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (activeRole == AppRole.BSEB_MCQ) BiharBlue else Color(0xFFB45309)
                )
            }
        }

        Surface(
            onClick = { onRoleSelected(AppRole.LIBRARY) },
            shape = RoundedCornerShape(14.dp),
            color = if (activeRole == AppRole.LIBRARY) BiharSky else Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (activeRole == AppRole.LIBRARY) BiharBlue else BiharBorder),
            modifier = Modifier.testTag("quick_link_library")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalLibrary,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = BiharBlue
                )
                Text(
                    text = "पुस्तकालय (e-Granthalaya)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BiharBlue
                )
            }
        }

        Surface(
            onClick = { onRoleSelected(AppRole.ABOUT) },
            shape = RoundedCornerShape(14.dp),
            color = if (activeRole == AppRole.ABOUT) BiharSky else Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (activeRole == AppRole.ABOUT) BiharBlue else BiharBorder),
            modifier = Modifier.testTag("quick_link_about")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = BiharBlue
                )
                Text(
                    text = "विद्यालय परिचय (About)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BiharBlue
                )
            }
        }

        Surface(
            onClick = { onRoleSelected(AppRole.ADMIN) },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
            modifier = Modifier.testTag("quick_link_students")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FormatListNumbered,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = BiharBlue
                )
                Text(
                    text = "नामांकित विद्यार्थी सूची",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = BiharTextDark
                )
            }
        }

        Surface(
            onClick = { onRoleSelected(AppRole.WEB) },
            shape = RoundedCornerShape(14.dp),
            color = if (activeRole == AppRole.WEB) BiharBlue else Color(0xFFEFF6FF),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (activeRole == AppRole.WEB) BiharBlue else Color(0xFFBFDBFE)),
            modifier = Modifier.testTag("quick_link_web_portal")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = if (activeRole == AppRole.WEB) Color.White else BiharBlue
                )
                Text(
                    text = "🌐 लाइव वेबसाइट दृश्य (Web View)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (activeRole == AppRole.WEB) Color.White else BiharBlue
                )
            }
        }
    }
}

@Composable
fun NoticeTickerBar(notices: List<Notice>) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(notices.size) {
        while (true) {
            delay(4000)
            if (notices.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % notices.size
            }
        }
    }

    val currentNotice = if (notices.isNotEmpty()) notices[currentIndex] else null

    Surface(
        color = BiharBlue,
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = TricolorSaffron,
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Notice",
                        tint = BiharTextDark,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "सूचना",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BiharTextDark
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (currentNotice != null) {
                AnimatedContent(
                    targetState = currentNotice,
                    transitionSpec = {
                        slideInVertically { height -> height } togetherWith
                                slideOutVertically { height -> -height }
                    },
                    modifier = Modifier.weight(1f),
                    label = "NoticeTickerAnimation"
                ) { notice ->
                    Text(
                        text = notice.title,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }
        }
    }
}
