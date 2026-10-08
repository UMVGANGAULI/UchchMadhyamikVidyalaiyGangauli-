package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.*
import com.example.data.bseb.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UiMessage(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val isError: Boolean = false
)

data class MainUiState(
    val activeRole: AppRole = AppRole.STUDENT,
    val isStudentLoggedIn: Boolean = true,
    val isTeacherLoggedIn: Boolean = false,
    val isAdminLoggedIn: Boolean = false,
    val isLibrarianLoggedIn: Boolean = false,

    // Login Form State
    val loginMobile: String = "9876543210",
    val loginPassword: String = "9876543210",
    val rememberMe: Boolean = true,

    // Current Logged-in Entities
    val currentStudent: Student = MockDataRepository.demoStudents[0],
    val currentTeacher: Teacher = MockDataRepository.demoTeachers[0],

    // Data lists
    val students: List<Student> = MockDataRepository.demoStudents,
    val teachers: List<Teacher> = MockDataRepository.demoTeachers,
    val books: List<Book> = MockDataRepository.demoBooks,
    val issueRecords: List<BookIssueRecord> = MockDataRepository.demoIssueRecords,
    val notices: List<Notice> = MockDataRepository.demoNotices,

    // Student Sub-tabs: "overview", "id_card", "attendance", "marks", "timetable", "library_card", "scholarship"
    val studentSubTab: String = "overview",

    // Teacher Sub-tabs: "dashboard", "attendance", "marks", "notices", "leave"
    val teacherSubTab: String = "attendance",
    val teacherAttendanceClass: Int = 10,
    val teacherAttendanceSection: String = "A",
    val attendanceRollCall: Map<String, Boolean> = emptyMap(),
    val attendanceSubmittedDate: String? = null,

    // Admin Sub-tabs: "dashboard", "students", "staff", "reports", "sync"
    val adminSubTab: String = "dashboard",
    val adminClassFilter: Int? = null, // null = all
    val adminSearchQuery: String = "",

    // Library Sub-tabs: "overview", "catalog", "issue_return", "form16", "ebooks"
    val librarySubTab: String = "overview",
    val librarySearchQuery: String = "",
    val libraryCategoryFilter: String = "सभी",
    val libraryClassFilter: String = "सभी",

    // UI Feedback
    val userMessage: UiMessage? = null,
    val isSyncingEshikshakosh: Boolean = false,

    // BSEB 10-Year MCQ Bank State & Rolling Engine
    val bsebSelectedClass: Int = 10,
    val bsebSelectedSubject: String = "गणित",
    val bsebSelectedYear: Int = 2025,
    val bsebActiveYears: List<Int> = emptyList(),
    val bsebAvailableSubjects: List<String> = emptyList(),
    val bsebActivePaper: BsebPaper? = null,
    val bsebMode: String = "test", // "test", "pdf", "engine"
    val bsebAnswers: Map<Int, Int> = emptyMap(), // qNo -> optionIndex
    val bsebIsSubmitted: Boolean = false,
    val bsebScore: Int = 0,
    val bsebCurrentQuestionIndex: Int = 0,
    val bsebEngineState: ExamRollingEngineState? = null,
    val bsebRolloverResult: RolloverResult? = null
)

class MainViewModel : ViewModel() {

    private val bsebRepo = BsebQuestionBankRepository()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        // Initialize attendance roll call for class 10
        val initialMap = _uiState.value.students
            .filter { it.studentClass == 10 }
            .associate { it.id to true }

        val years = bsebRepo.getActiveYears()
        val subjects = bsebRepo.getAvailableSubjects(10)
        val initialPaper = bsebRepo.getPaper(10, "गणित", years.first())
        val engine = bsebRepo.getEngineState()

        _uiState.update {
            it.copy(
                attendanceRollCall = initialMap,
                bsebActiveYears = years,
                bsebAvailableSubjects = subjects,
                bsebActivePaper = initialPaper,
                bsebEngineState = engine
            )
        }
    }

    fun setActiveRole(role: AppRole) {
        _uiState.update { it.copy(activeRole = role) }
    }

    fun updateLoginMobile(value: String) {
        _uiState.update { it.copy(loginMobile = value) }
    }

    fun updateLoginPassword(value: String) {
        _uiState.update { it.copy(loginPassword = value) }
    }

    fun toggleRememberMe(value: Boolean) {
        _uiState.update { it.copy(rememberMe = value) }
    }

    fun fillDemoLogin(role: AppRole) {
        when (role) {
            AppRole.STUDENT -> {
                _uiState.update {
                    it.copy(
                        loginMobile = "9876543210",
                        loginPassword = "9876543210",
                        isStudentLoggedIn = true,
                        currentStudent = MockDataRepository.demoStudents[0]
                    )
                }
                showMessage("छात्र अमित कुमार (कक्षा 10, रोल 14) के रूप में लॉगिन सफल!")
            }
            AppRole.TEACHER -> {
                _uiState.update {
                    it.copy(
                        loginMobile = "9876500010",
                        loginPassword = "9876500010",
                        isTeacherLoggedIn = true,
                        currentTeacher = MockDataRepository.demoTeachers[0]
                    )
                }
                showMessage("शिक्षक मुकेश कुमार (PGT गणित) के रूप में लॉगिन सफल!")
            }
            AppRole.ADMIN -> {
                _uiState.update {
                    it.copy(
                        loginMobile = "9876500001",
                        loginPassword = "admin",
                        isAdminLoggedIn = true
                    )
                }
                showMessage("प्रशासक / प्रधानाध्यापक पोर्टल लॉगिन सफल!")
            }
            AppRole.LIBRARY -> {
                _uiState.update {
                    it.copy(
                        loginMobile = "9876500002",
                        loginPassword = "library",
                        isLibrarianLoggedIn = true
                    )
                }
                showMessage("पुस्तकालयाध्यक्ष (e-Granthalaya) लॉगिन सफल!")
            }
            AppRole.ABOUT -> {}
            AppRole.WEB -> {}
            AppRole.BSEB_MCQ -> {}
        }
    }

    fun loginStudent() {
        _uiState.update { it.copy(isStudentLoggedIn = true) }
        showMessage("छात्र पोर्टल में सफल लॉगिन!")
    }

    fun loginTeacher() {
        _uiState.update { it.copy(isTeacherLoggedIn = true) }
        showMessage("शिक्षक पोर्टल में सफल लॉगिन!")
    }

    fun loginAdmin() {
        _uiState.update { it.copy(isAdminLoggedIn = true) }
        showMessage("प्रशासक पोर्टल में सफल लॉगिन!")
    }

    fun loginLibrarian() {
        _uiState.update { it.copy(isLibrarianLoggedIn = true) }
        showMessage("पुस्तकालय पोर्टल में सफल लॉगिन!")
    }

    fun logoutCurrentRole() {
        when (_uiState.value.activeRole) {
            AppRole.STUDENT -> _uiState.update { it.copy(isStudentLoggedIn = false) }
            AppRole.TEACHER -> _uiState.update { it.copy(isTeacherLoggedIn = false) }
            AppRole.ADMIN -> _uiState.update { it.copy(isAdminLoggedIn = false) }
            AppRole.LIBRARY -> _uiState.update { it.copy(isLibrarianLoggedIn = false) }
            AppRole.ABOUT -> {}
            AppRole.WEB -> {}
            AppRole.BSEB_MCQ -> {}
        }
        showMessage("सफलतापूर्वक लॉगआउट किया गया")
    }

    // Sub-tab changers
    fun setStudentSubTab(tab: String) {
        _uiState.update { it.copy(studentSubTab = tab) }
    }

    fun setTeacherSubTab(tab: String) {
        _uiState.update { it.copy(teacherSubTab = tab) }
    }

    fun setAdminSubTab(tab: String) {
        _uiState.update { it.copy(adminSubTab = tab) }
    }

    fun setLibrarySubTab(tab: String) {
        _uiState.update { it.copy(librarySubTab = tab) }
    }

    // Teacher Attendance Actions
    fun changeTeacherAttendanceClass(cls: Int) {
        val newMap = _uiState.value.students
            .filter { it.studentClass == cls }
            .associate { it.id to true }
        _uiState.update {
            it.copy(
                teacherAttendanceClass = cls,
                attendanceRollCall = newMap,
                attendanceSubmittedDate = null
            )
        }
    }

    fun toggleStudentAttendance(studentId: String) {
        val current = _uiState.value.attendanceRollCall[studentId] ?: true
        val updated = _uiState.value.attendanceRollCall.toMutableMap()
        updated[studentId] = !current
        _uiState.update { it.copy(attendanceRollCall = updated) }
    }

    fun markAllPresent() {
        val updated = _uiState.value.attendanceRollCall.mapValues { true }
        _uiState.update { it.copy(attendanceRollCall = updated) }
        showMessage("सभी विद्यार्थियों को 'उपस्थित' चिह्नित किया गया")
    }

    fun submitDailyAttendance() {
        val presentCount = _uiState.value.attendanceRollCall.values.count { it }
        val total = _uiState.value.attendanceRollCall.size
        val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        _uiState.update { it.copy(attendanceSubmittedDate = today) }
        showMessage("कक्षा ${_uiState.value.teacherAttendanceClass} की दैनिक उपस्थिति ($presentCount/$total उपस्थित) e-ShikshaKosh पर सफलता से दर्ज की गई!")
    }

    // Admin Filters
    fun setAdminClassFilter(cls: Int?) {
        _uiState.update { it.copy(adminClassFilter = cls) }
    }

    fun setAdminSearchQuery(q: String) {
        _uiState.update { it.copy(adminSearchQuery = q) }
    }

    fun syncWithEShikshakosh() {
        _uiState.update { it.copy(isSyncingEshikshakosh = true) }
        // Simulate quick sync
        _uiState.update {
            it.copy(
                isSyncingEshikshakosh = false
            )
        }
        showMessage("e-ShikshaKosh एवं UDISE+ (10301901805) डाटा सिंक्रोनाइज़ेशन पूर्ण!")
    }

    // Library Actions
    fun setLibrarySearchQuery(q: String) {
        _uiState.update { it.copy(librarySearchQuery = q) }
    }

    fun setLibraryCategoryFilter(cat: String) {
        _uiState.update { it.copy(libraryCategoryFilter = cat) }
    }

    fun setLibraryClassFilter(cls: String) {
        _uiState.update { it.copy(libraryClassFilter = cls) }
    }

    fun addBook(
        title: String,
        hindiTitle: String,
        author: String,
        publisher: String,
        subject: String,
        targetClass: String,
        category: String,
        rackNo: String,
        isbn: String
    ) {
        val count = _uiState.value.books.size + 1
        val accNo = String.format(Locale.getDefault(), "UMV/LIB/2025/%04d", count)
        val newBook = Book(
            id = "BK_${System.currentTimeMillis()}",
            accessionNo = accNo,
            isbn = if (isbn.isNotBlank()) isbn else "978-81-0000-${count}",
            title = title,
            hindiTitle = hindiTitle,
            author = author,
            publisher = publisher,
            subject = subject,
            targetClass = targetClass,
            category = category,
            rackNo = rackNo,
            eGranthalayaSyncId = "EG4-UMV-${1000 + count}"
        )
        val updatedList = _uiState.value.books + newBook
        _uiState.update { it.copy(books = updatedList) }
        showMessage("पुस्तक '$hindiTitle' (क्रमांक: $accNo) सफलतापूर्वक जोड़ी गई!")
    }

    fun issueBookToStudent(
        bookId: String,
        studentId: String
    ) {
        val book = _uiState.value.books.find { it.id == bookId }
        val student = _uiState.value.students.find { it.id == studentId }

        if (book == null || student == null) {
            showMessage("पुस्तक या विद्यार्थी नहीं मिला", isError = true)
            return
        }

        if (book.isIssued) {
            showMessage("यह पुस्तक पहले से जारी है!", isError = true)
            return
        }

        // Bihar Govt Norm: Class 9-10 max 2 books, Class 11-12 max 3 books
        val currentIssuedCount = _uiState.value.issueRecords.count {
            it.studentName == student.name && !it.isReturned
        }
        val maxAllowed = if (student.studentClass <= 10) 2 else 3

        if (currentIssuedCount >= maxAllowed) {
            showMessage(
                "बिहार सरकारी नियमानुसार कक्षा ${student.studentClass} के लिए अधिकतम $maxAllowed पुस्तकें ही जारी की जा सकती हैं!",
                isError = true
            )
            return
        }

        val todayStr = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        // 14 days later due date
        val dueStr = "24/10/2025"

        val newRecord = BookIssueRecord(
            id = "ISS_${System.currentTimeMillis()}",
            bookId = book.id,
            bookTitle = book.hindiTitle,
            accessionNo = book.accessionNo,
            studentName = student.name,
            rollNo = student.rollNo,
            studentClass = student.studentClass,
            issueDate = todayStr,
            dueDate = dueStr,
            fineAmount = 0,
            condition = "उत्कृष्ट (Good)",
            isReturned = false
        )

        val updatedBooks = _uiState.value.books.map {
            if (it.id == bookId) {
                it.copy(
                    isIssued = true,
                    issuedToStudentName = student.name,
                    issuedToRoll = student.rollNo,
                    issuedToClass = student.studentClass,
                    issueDate = todayStr,
                    dueDate = dueStr
                )
            } else it
        }

        val updatedRecords = _uiState.value.issueRecords + newRecord
        _uiState.update { it.copy(books = updatedBooks, issueRecords = updatedRecords) }
        showMessage("पुस्तक '${book.hindiTitle}' छात्र ${student.name} को जारी की गई (अंतिम तिथि: $dueStr)")
    }

    fun returnBook(recordId: String, condition: String) {
        val record = _uiState.value.issueRecords.find { it.id == recordId } ?: return
        val todayStr = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        val updatedRecords = _uiState.value.issueRecords.map {
            if (it.id == recordId) {
                it.copy(
                    isReturned = true,
                    returnDate = todayStr,
                    condition = condition
                )
            } else it
        }

        val updatedBooks = _uiState.value.books.map {
            if (it.id == record.bookId) {
                it.copy(
                    isIssued = false,
                    issuedToStudentName = null,
                    issuedToRoll = null,
                    issuedToClass = null,
                    issueDate = null,
                    dueDate = null
                )
            } else it
        }

        _uiState.update { it.copy(issueRecords = updatedRecords, books = updatedBooks) }
        val fineMsg = if (record.fineAmount > 0) " (विलंब शुल्क: ₹${record.fineAmount} जमा)" else ""
        showMessage("पुस्तक '${record.bookTitle}' जमा कर ली गई$fineMsg")
    }

    // BSEB 10-Year Question Bank & Rolling Engine Methods
    fun setBsebClass(cls: Int) {
        val subjects = bsebRepo.getAvailableSubjects(cls)
        val subject = subjects.firstOrNull() ?: "गणित"
        val paper = bsebRepo.getPaper(cls, subject, _uiState.value.bsebSelectedYear)
        _uiState.update {
            it.copy(
                bsebSelectedClass = cls,
                bsebAvailableSubjects = subjects,
                bsebSelectedSubject = subject,
                bsebActivePaper = paper,
                bsebAnswers = emptyMap(),
                bsebIsSubmitted = false,
                bsebScore = 0,
                bsebCurrentQuestionIndex = 0
            )
        }
    }

    fun setBsebSubject(subject: String) {
        val paper = bsebRepo.getPaper(_uiState.value.bsebSelectedClass, subject, _uiState.value.bsebSelectedYear)
        _uiState.update {
            it.copy(
                bsebSelectedSubject = subject,
                bsebActivePaper = paper,
                bsebAnswers = emptyMap(),
                bsebIsSubmitted = false,
                bsebScore = 0,
                bsebCurrentQuestionIndex = 0
            )
        }
    }

    fun setBsebYear(year: Int) {
        val paper = bsebRepo.getPaper(_uiState.value.bsebSelectedClass, _uiState.value.bsebSelectedSubject, year)
        _uiState.update {
            it.copy(
                bsebSelectedYear = year,
                bsebActivePaper = paper,
                bsebAnswers = emptyMap(),
                bsebIsSubmitted = false,
                bsebScore = 0,
                bsebCurrentQuestionIndex = 0
            )
        }
    }

    fun setBsebMode(mode: String) {
        _uiState.update { it.copy(bsebMode = mode) }
    }

    fun selectBsebOption(qNo: Int, optionIndex: Int) {
        if (_uiState.value.bsebIsSubmitted) return
        val currentAnswers = _uiState.value.bsebAnswers.toMutableMap()
        currentAnswers[qNo] = optionIndex
        _uiState.update { it.copy(bsebAnswers = currentAnswers) }
    }

    fun submitBsebTest() {
        val paper = _uiState.value.bsebActivePaper ?: return
        var correctCount = 0
        paper.questions.forEach { q ->
            val userSelected = _uiState.value.bsebAnswers[q.qNo]
            if (userSelected == q.correctIndex) {
                correctCount++
            }
        }
        val pct = if (paper.questions.isNotEmpty()) (correctCount * 100) / paper.questions.size else 0
        _uiState.update {
            it.copy(
                bsebIsSubmitted = true,
                bsebScore = correctCount
            )
        }
        showMessage("टेस्ट संपन्न! आपका प्राप्तांक: $correctCount / ${paper.questions.size} ($pct%)")
    }

    fun resetBsebTest() {
        _uiState.update {
            it.copy(
                bsebAnswers = emptyMap(),
                bsebIsSubmitted = false,
                bsebScore = 0,
                bsebCurrentQuestionIndex = 0
            )
        }
        showMessage("टेस्ट पुनः प्रारंभ किया गया")
    }

    fun triggerAnnualRollover() {
        val result = bsebRepo.performAnnualRollover()
        val years = bsebRepo.getActiveYears()
        val currentYear = years.first()
        val paper = bsebRepo.getPaper(_uiState.value.bsebSelectedClass, _uiState.value.bsebSelectedSubject, currentYear)
        val engine = bsebRepo.getEngineState()

        _uiState.update {
            it.copy(
                bsebActiveYears = years,
                bsebSelectedYear = currentYear,
                bsebActivePaper = paper,
                bsebEngineState = engine,
                bsebRolloverResult = result,
                bsebAnswers = emptyMap(),
                bsebIsSubmitted = false
            )
        }
        showMessage("रोलिंग इंजन संपन्न: वर्ष ${result.newExamYearAdded} के नए MCQ जोड़े गए एवं 10वां वर्ष (${result.oldestYearDeleted}) हटाया गया!")
    }

    fun showMessage(msg: String, isError: Boolean = false) {
        _uiState.update { it.copy(userMessage = UiMessage(text = msg, isError = isError)) }
    }

    fun clearMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}
