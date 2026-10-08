package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRole
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back button behavior for deep tabs and roles
    BackHandler(enabled = uiState.activeRole != AppRole.STUDENT || uiState.studentSubTab != "overview") {
        if (uiState.activeRole != AppRole.STUDENT) {
            viewModel.setActiveRole(AppRole.STUDENT)
        } else if (uiState.studentSubTab != "overview") {
            viewModel.setStudentSubTab("overview")
        }
    }

    // Trigger snackbar when user message changes
    LaunchedEffect(uiState.userMessage?.id) {
        uiState.userMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg.text,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessage()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_scaffold"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                GovtTopBar()
                HeaderMockupPreview()
                ThreeMainTabsBar(
                    activeRole = uiState.activeRole,
                    onRoleSelected = { viewModel.setActiveRole(it) }
                )
                NoticeTickerBar(notices = uiState.notices)
                QuickUtilityNavLinks(
                    activeRole = uiState.activeRole,
                    onRoleSelected = { viewModel.setActiveRole(it) }
                )
            }
        },
        containerColor = BiharBackground
    ) { innerPadding ->
        if (uiState.activeRole == AppRole.WEB) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                WebPortalScreen()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                when (uiState.activeRole) {
                    AppRole.STUDENT -> StudentPortal(uiState = uiState, viewModel = viewModel)
                    AppRole.TEACHER -> TeacherPortal(uiState = uiState, viewModel = viewModel)
                    AppRole.ADMIN -> AdminPortal(uiState = uiState, viewModel = viewModel)
                    AppRole.LIBRARY -> LibraryPortal(uiState = uiState, viewModel = viewModel)
                    AppRole.ABOUT -> SchoolProfileScreen()
                    AppRole.WEB -> WebPortalScreen()
                    AppRole.BSEB_MCQ -> BsebMcqScreen(uiState = uiState, viewModel = viewModel)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Official Bihar Govt Footer
                OfficialGovtFooter()
            }
        }
    }
}

@Composable
fun OfficialGovtFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BiharNavyDark)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TricolorBorder()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "बिहार सरकार • शिक्षा विभाग • e-ShikshaKosh पोर्टल",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "उच्च माध्यमिक विद्यालय, गंगौली • प्रखंड: सिमरी, जिला: बक्सर (बिहार)",
            fontSize = 10.sp,
            color = BiharSky,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = "UDISE+: 10301901805 • सत्र: 2025-2026 • सर्वाधिकार सुरक्षित",
            fontSize = 9.sp,
            color = Color.White.copy(alpha = 0.65f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
