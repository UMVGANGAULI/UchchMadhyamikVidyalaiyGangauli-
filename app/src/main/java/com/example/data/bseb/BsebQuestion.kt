package com.example.data.bseb

data class BsebMcq(
    val id: String,
    val qNo: Int,
    val questionHindi: String,
    val questionEnglish: String? = null,
    val options: List<String>, // Exactly 4 options: A, B, C, D
    val correctIndex: Int,     // 0 = A, 1 = B, 2 = C, 3 = D
    val explanation: String,
    val chapterOrTopic: String
)

data class BsebPaper(
    val id: String,
    val classGrade: Int,       // 10, 12, 9, 11
    val stream: String,        // "General", "Science", "Arts"
    val subject: String,       // "गणित", "विज्ञान", "सामाजिक विज्ञान", etc.
    val subjectCode: String,   // "110", "112", etc.
    val year: Int,             // e.g., 2025, 2024, ...
    val totalQuestions: Int = 50,
    val totalMarks: Int = 50,
    val durationMinutes: Int = 60,
    val questions: List<BsebMcq>
)

data class ExamRollingEngineState(
    val currentWindowStartYear: Int, // e.g., 2016
    val currentWindowEndYear: Int,   // e.g., 2025
    val maxWindowYears: Int = 10,
    val lastRolledDate: String,
    val nextScheduledRollYear: Int,
    val totalActivePapersCount: Int,
    val isAutoUpdateEnabled: Boolean = true
)

data class RolloverResult(
    val newExamYearAdded: Int,
    val oldestYearDeleted: Int,
    val previousWindow: String,
    val newWindow: String,
    val affectedSubjectsCount: Int,
    val message: String
)
