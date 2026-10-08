package com.example.data

enum class AppRole(val labelHindi: String, val labelEnglish: String) {
    STUDENT("छात्र", "Student"),
    TEACHER("शिक्षक", "Teacher"),
    ADMIN("प्रशासक", "Admin"),
    LIBRARY("पुस्तकालय", "Library"),
    ABOUT("विद्यालय परिचय", "About School"),
    WEB("वेबसाइट पोर्टल", "Website"),
    BSEB_MCQ("10-वर्षीय MCQ बैंक", "BSEB MCQs")
}

data class Student(
    val id: String,
    val rollNo: Int,
    val name: String,
    val studentClass: Int, // 9, 10, 11, 12
    val section: String,   // A, B
    val stream: String,    // General, Science, Arts, Commerce
    val mobile: String,
    val fatherName: String,
    val motherName: String,
    val dob: String,
    val bloodGroup: String,
    val attendancePercentage: Float,
    val scholarshipStatus: String,
    val scholarshipScheme: String,
    val address: String = "ग्राम + पो: गंगौली, सिमरी, बक्सर",
    val udiseStudentId: String
)

data class Teacher(
    val id: String,
    val name: String,
    val designation: String, // PGT Mathematics, TGT Science, etc.
    val subject: String,
    val mobile: String,
    val assignedClasses: String,
    val qualification: String,
    val email: String,
    val biometricPunchTime: String = "08:48 AM"
)

data class Book(
    val id: String,
    val accessionNo: String, // format: UMV/LIB/2025/0001
    val isbn: String,
    val title: String,
    val hindiTitle: String,
    val author: String,
    val publisher: String,
    val subject: String,
    val targetClass: String, // 9, 10, 11, 12, or All
    val category: String, // पाठ्यपुस्तक, संदर्भ, कहानी, प्रतियोगी
    val rackNo: String,
    val isIssued: Boolean = false,
    val issuedToStudentName: String? = null,
    val issuedToRoll: Int? = null,
    val issuedToClass: Int? = null,
    val issueDate: String? = null,
    val dueDate: String? = null,
    val eGranthalayaSyncId: String
)

data class BookIssueRecord(
    val id: String,
    val bookId: String,
    val bookTitle: String,
    val accessionNo: String,
    val studentName: String,
    val rollNo: Int,
    val studentClass: Int,
    val issueDate: String,
    val dueDate: String,
    val returnDate: String? = null,
    val fineAmount: Int = 0,
    val condition: String = "उत्कृष्ट (Good)", // Good, Damaged, Lost
    val isReturned: Boolean = false
)

data class MarkItem(
    val subject: String,
    val totalMarks: Int,
    val marksObtained: Int,
    val grade: String
)

data class TimeTableEntry(
    val periodNo: Int,
    val timeSlot: String,
    val subject: String,
    val teacherName: String,
    val roomNo: String
)

data class Notice(
    val id: String,
    val title: String,
    val date: String,
    val category: String, // BSEB, छात्रवृत्ति, पुस्तकालय, विद्यालय
    val isUrgent: Boolean = false,
    val linkText: String = "विवरण देखें"
)
