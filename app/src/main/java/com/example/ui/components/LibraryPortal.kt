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
import com.example.data.Book
import com.example.data.BookIssueRecord
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainUiState
import com.example.ui.viewmodel.MainViewModel

@Composable
fun LibraryPortal(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    if (!uiState.isLibrarianLoggedIn) {
        LibrarianLoginCard(viewModel = viewModel)
    } else {
        LibraryDashboard(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun LibrarianLoginCard(viewModel: MainViewModel) {
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
                        imageVector = Icons.Default.LocalLibrary,
                        contentDescription = "Library Login",
                        tint = BiharBlue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ई-ग्रंथालय पुस्तकालय पोर्टल (e-Granthalaya 4.0)",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue,
                textAlign = TextAlign.Center
            )
            Text(
                text = "उच्च माध्यमिक विद्यालय, गंगौली • कक्षा 9-12 केंद्रीय वाचनालय",
                fontSize = 11.5.sp,
                color = BiharTextMid,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Button(
                onClick = { viewModel.loginLibrarian() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("librarian_login_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "पुस्तकालयाध्यक्ष लॉगिन करें (Login)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { viewModel.fillDemoLogin(com.example.data.AppRole.LIBRARY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("demo_library_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = BiharSky),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder)
            ) {
                Text(
                    text = "त्वरित डेमो लॉगिन (Shambhu Sharan - Librarian)",
                    fontSize = 12.sp,
                    color = BiharBlue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun LibraryDashboard(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    var showAddBookDialog by remember { mutableStateOf(false) }
    var showIssueBookDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Librarian Header
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
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "श्री शंभू शरण प्रसाद",
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = BiharTextDark
                        )
                        Text(
                            text = "पुस्तकालयाध्यक्ष (e-Granthalaya प्रभारी)",
                            fontSize = 11.sp,
                            color = BiharBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "वाचनालय समय: प्रातः 09:00 से 04:00 PM",
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
                    modifier = Modifier.testTag("librarian_logout_btn")
                ) {
                    Text("लॉगआउट", fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sub-tabs
        val subTabs = listOf(
            "overview" to "डैशबोर्ड सारांश",
            "catalog" to "पुस्तक सूची (Catalog)",
            "issue_return" to "निर्गमन एवं वापसी",
            "form16" to "दैनिक रजिस्टर (Form 16)",
            "ebooks" to "डिजिटल ई-बुक्स"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            subTabs.forEach { (tabKey, tabTitle) ->
                val isSelected = uiState.librarySubTab == tabKey
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setLibrarySubTab(tabKey) },
                    label = { Text(tabTitle, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("library_subtab_$tabKey")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState.librarySubTab) {
            "overview" -> LibraryOverviewSection(
                uiState = uiState,
                onAddBook = { showAddBookDialog = true },
                onIssueBook = { showIssueBookDialog = true }
            )
            "catalog" -> LibraryCatalogSection(
                uiState = uiState,
                viewModel = viewModel,
                onAddBook = { showAddBookDialog = true }
            )
            "issue_return" -> LibraryIssueReturnSection(
                uiState = uiState,
                viewModel = viewModel,
                onIssueBook = { showIssueBookDialog = true }
            )
            "form16" -> LibraryForm16Register(uiState = uiState)
            "ebooks" -> LibraryEBooksSection(viewModel = viewModel)
            else -> LibraryOverviewSection(
                uiState = uiState,
                onAddBook = { showAddBookDialog = true },
                onIssueBook = { showIssueBookDialog = true }
            )
        }
    }

    if (showAddBookDialog) {
        AddBookDialog(
            onDismiss = { showAddBookDialog = false },
            onAdd = { title, hindiTitle, author, pub, subj, cls, cat, rack, isbn ->
                viewModel.addBook(title, hindiTitle, author, pub, subj, cls, cat, rack, isbn)
                showAddBookDialog = false
            }
        )
    }

    if (showIssueBookDialog) {
        IssueBookDialog(
            uiState = uiState,
            onDismiss = { showIssueBookDialog = false },
            onIssue = { bookId, studentId ->
                viewModel.issueBookToStudent(bookId, studentId)
                showIssueBookDialog = false
            }
        )
    }
}

@Composable
fun LibraryOverviewSection(
    uiState: MainUiState,
    onAddBook: () -> Unit,
    onIssueBook: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Exact 4 Cards Row from Bihar Govt prompt
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "कुल पुस्तकें",
                value = "4,250",
                subtitle = "रजिस्टर स्टॉक",
                icon = Icons.Default.Book,
                accentColor = BiharBlue,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "जारी पुस्तकें",
                value = "312",
                subtitle = "विद्यार्थियों के पास",
                icon = Icons.Default.Output,
                accentColor = TricolorSaffron,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "उपलब्ध पुस्तकें",
                value = "3,938",
                subtitle = "अलमारी में उपलब्ध",
                icon = Icons.Default.CheckCircle,
                accentColor = TricolorGreen,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "अतिदेय (Overdue)",
                value = "18",
                subtitle = "विलंब शुल्क लागू",
                icon = Icons.Default.Warning,
                accentColor = RedOverdue,
                modifier = Modifier.weight(1f)
            )
        }

        // Bihar Govt Library Rules Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BiharSky),
            border = androidx.compose.foundation.BorderStroke(1.dp, BiharSkyBorder),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "बिहार शिक्षा विभाग पुस्तकालय नियमावली (Norms):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BiharBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "• कक्षा 9-10: अधिकतम 2 पुस्तकें | कक्षा 11-12: अधिकतम 3 पुस्तकें",
                    fontSize = 10.5.sp,
                    color = BiharTextDark
                )
                Text(
                    text = "• अवधि: पाठ्यपुस्तक 14 दिन, संदर्भ ग्रंथ 7 दिन",
                    fontSize = 10.5.sp,
                    color = BiharTextDark
                )
                Text(
                    text = "• विलंब शुल्क (Fine): 1-7 दिन = ₹1/दिन | 8-15 दिन = ₹2/दिन | 15+ दिन = ₹5/दिन",
                    fontSize = 10.5.sp,
                    color = BiharTextDark
                )
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onIssueBook,
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.AssignmentReturn, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("पुस्तक जारी करें", fontSize = 11.5.sp)
            }

            Button(
                onClick = onAddBook,
                colors = ButtonDefaults.buttonColors(containerColor = TricolorGreen),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("नई पुस्तक जोड़ें", fontSize = 11.5.sp)
            }
        }
    }
}

@Composable
fun LibraryCatalogSection(
    uiState: MainUiState,
    viewModel: MainViewModel,
    onAddBook: () -> Unit
) {
    var searchQuery = uiState.librarySearchQuery
    var selectedCat = uiState.libraryCategoryFilter

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ई-ग्रंथालय स्टॉक कैटलॉग (Class 9-12)",
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            SmallFloatingActionButton(
                onClick = onAddBook,
                containerColor = BiharBlue,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book")
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setLibrarySearchQuery(it) },
            placeholder = { Text("शीर्षक, लेखक, विषय अथवा Accession No...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Category filter chips
        val categories = listOf("सभी", "पाठ्यपुस्तक", "संदर्भ", "कहानी एवं साहित्य", "प्रतियोगी")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { cat ->
                FilterChip(
                    selected = selectedCat == cat,
                    onClick = { viewModel.setLibraryCategoryFilter(cat) },
                    label = { Text(cat, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BiharBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        val filtered = uiState.books.filter { book ->
            (selectedCat == "सभी" || book.category == selectedCat) &&
                    (searchQuery.isBlank() ||
                            book.hindiTitle.contains(searchQuery, ignoreCase = true) ||
                            book.title.contains(searchQuery, ignoreCase = true) ||
                            book.author.contains(searchQuery, ignoreCase = true) ||
                            book.accessionNo.contains(searchQuery, ignoreCase = true) ||
                            book.subject.contains(searchQuery, ignoreCase = true))
        }

        Text(
            text = "सूचीबद्ध पुस्तकें: ${filtered.size}",
            fontSize = 11.sp,
            color = BiharTextLight,
            fontWeight = FontWeight.SemiBold
        )

        filtered.forEach { book ->
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = book.hindiTitle,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextDark
                            )
                            Text(
                                text = "${book.title} • लेखक: ${book.author}",
                                fontSize = 10.5.sp,
                                color = BiharTextMid
                            )
                        }

                        Surface(
                            color = if (book.isIssued) RedSurface else GreenSurface,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (book.isIssued) "निर्गत (Issued)" else "उपलब्ध (Available)",
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (book.isIssued) Color.Red else GreenText,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Acc: ${book.accessionNo} • ${book.rackNo}",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = BiharBlue
                        )
                        Text(
                            text = "कक्षा: ${book.targetClass} | ${book.category}",
                            fontSize = 10.sp,
                            color = BiharTextLight
                        )
                    }

                    if (book.isIssued && book.issuedToStudentName != null) {
                        Surface(
                            color = BiharBackground,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)
                        ) {
                            Text(
                                text = "छात्र: ${book.issuedToStudentName} (रोल: ${book.issuedToRoll}) • देय तिथि: ${book.dueDate}",
                                fontSize = 9.5.sp,
                                color = BiharTextDark,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryIssueReturnSection(
    uiState: MainUiState,
    viewModel: MainViewModel,
    onIssueBook: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "सक्रिय निर्गमन एवं वापसी रजिस्टर",
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Button(
                onClick = onIssueBook,
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("+ नई पुस्तक जारी करें", fontSize = 11.sp)
            }
        }

        uiState.issueRecords.forEach { record ->
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = record.bookTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BiharTextDark
                            )
                            Text(
                                text = "छात्र: ${record.studentName} (कक्षा ${record.studentClass}, रोल ${record.rollNo})",
                                fontSize = 11.sp,
                                color = BiharBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Surface(
                            color = if (record.isReturned) GreenSurface else if (record.fineAmount > 0) RedSurface else BiharSky,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (record.isReturned) "वापस प्राप्त" else if (record.fineAmount > 0) "विलंब (Overdue)" else "निर्गत",
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (record.isReturned) GreenText else if (record.fineAmount > 0) Color.Red else BiharBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "परिग्रहण सं: ${record.accessionNo} • जारी तिथि: ${record.issueDate} • देय तिथि: ${record.dueDate}",
                        fontSize = 10.sp,
                        color = BiharTextLight
                    )

                    if (!record.isReturned) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (record.fineAmount > 0) {
                                Text(
                                    text = "विलंब शुल्क: ₹${record.fineAmount}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            } else {
                                Text(
                                    text = "शुल्क: ₹0 (समय सीमा में)",
                                    fontSize = 10.sp,
                                    color = GreenText
                                )
                            }

                            Button(
                                onClick = { viewModel.returnBook(record.id, "उत्कृष्ट (Good)") },
                                colors = ButtonDefaults.buttonColors(containerColor = TricolorGreen),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text("जमा लें (Return)", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryForm16Register(uiState: MainUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "दैनिक पुस्तक निर्गमन रजिस्टर (प्रारूप 16 - Form 16)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BiharBlue
            )
            Text(
                text = "बिहार राजकीय विद्यालय पुस्तकालय नियमावली के अंतर्गत दैनिक प्रविष्टि",
                fontSize = 11.sp,
                color = BiharTextLight,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            uiState.issueRecords.take(5).forEachIndexed { idx, rec ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${idx + 1}. ${rec.accessionNo} - ${rec.bookTitle.take(15)}...",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = BiharTextDark
                    )
                    Text(
                        text = "${rec.studentName} (${rec.issueDate})",
                        fontSize = 10.sp,
                        color = BiharBlue
                    )
                }
                Divider(color = BiharBorder.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun LibraryEBooksSection(viewModel: MainViewModel) {
    val ebooks = listOf(
        "कक्षा 10 गणित एनसीईआरटी (संपूर्ण पाठ्यपुस्तक PDF)",
        "कक्षा 10 विज्ञान (SCERT बिहार बोर्ड आधिकारिक संस्करण)",
        "कक्षा 12 भौतिकी भाग 1 एवं 2",
        "कक्षा 12 रसायन विज्ञान भाग 1 एवं 2",
        "कक्षा 11 भारत का संविधान सिद्धांत और व्यवहार",
        "मुंशी प्रेमचंद संकलित कहानियां (डिजिटल वाचनालय)"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "बिहार बोर्ड एवं NCERT डिजिटल ई-बुक्स रिपोजिटरी",
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold,
            color = BiharBlue
        )

        ebooks.forEach { ebook ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BiharBorder),
                shape = RoundedCornerShape(8.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = Color.Red)
                        Text(text = ebook, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = BiharTextDark)
                    }

                    OutlinedButton(
                        onClick = { viewModel.showMessage("डिजिटल ई-बुक PDF सफलतापूर्वक लोड हुई!") },
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("डाउनलोड", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AddBookDialog(
    onDismiss: () -> Unit,
    onAdd: (title: String, hindiTitle: String, author: String, publisher: String, subject: String, targetClass: String, category: String, rackNo: String, isbn: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var hindiTitle by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var publisher by remember { mutableStateOf("NCERT / Bihar State Textbook") }
    var subject by remember { mutableStateOf("गणित") }
    var targetClass by remember { mutableStateOf("10") }
    var category by remember { mutableStateOf("पाठ्यपुस्तक") }
    var rackNo by remember { mutableStateOf("Rack R-01") }
    var isbn by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("नई पुस्तक जोड़ें (e-Granthalaya Catalog)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = BiharBlue)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = hindiTitle,
                    onValueChange = { hindiTitle = it },
                    label = { Text("हिंदी शीर्षक (Title in Hindi)") },
                    placeholder = { Text("उदा. गणित कक्षा 10") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("अंग्रेजी शीर्षक (Title in English)") },
                    placeholder = { Text("Mathematics Class 10") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("लेखक (Author)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = rackNo,
                    onValueChange = { rackNo = it },
                    label = { Text("अलमारी / रैक संख्या (Rack No)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (hindiTitle.isNotBlank()) {
                        onAdd(title.ifBlank { hindiTitle }, hindiTitle, author.ifBlank { "NCERT Board" }, publisher, subject, targetClass, category, rackNo, isbn)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("सुरक्षित करें (Add Book)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("रद्द करें")
            }
        }
    )
}

@Composable
fun IssueBookDialog(
    uiState: MainUiState,
    onDismiss: () -> Unit,
    onIssue: (bookId: String, studentId: String) -> Unit
) {
    val availableBooks = uiState.books.filter { !it.isIssued }
    var selectedBookId by remember { mutableStateOf(availableBooks.firstOrNull()?.id ?: "") }
    var selectedStudentId by remember { mutableStateOf(uiState.students.firstOrNull()?.id ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("पुस्तक जारी करें (Issue Book)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = BiharBlue)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "उपलब्ध पुस्तक चुनें:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BiharTextDark
                )
                availableBooks.take(4).forEach { book ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedBookId == book.id,
                            onClick = { selectedBookId = book.id }
                        )
                        Text(
                            text = "${book.accessionNo} - ${book.hindiTitle}",
                            fontSize = 11.sp,
                            color = BiharTextDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "विद्यार्थी चुनें:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BiharTextDark
                )
                uiState.students.take(4).forEach { student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedStudentId == student.id,
                            onClick = { selectedStudentId = student.id }
                        )
                        Text(
                            text = "${student.name} (कक्षा ${student.studentClass}, रोल ${student.rollNo})",
                            fontSize = 11.sp,
                            color = BiharTextDark
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedBookId.isNotBlank() && selectedStudentId.isNotBlank()) {
                        onIssue(selectedBookId, selectedStudentId)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BiharBlue)
            ) {
                Text("जारी करें (Issue)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("रद्द करें")
            }
        }
    )
}
