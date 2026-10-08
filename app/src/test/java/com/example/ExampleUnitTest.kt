package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testBsebTenYearRollingEngine() {
    val repo = com.example.data.bseb.BsebQuestionBankRepository()
    val activeYears = repo.getActiveYears()

    // 1. Exactly 10 years active
    assertEquals(10, activeYears.size)
    assertEquals(2025, activeYears.first())
    assertEquals(2016, activeYears.last())

    // 2. Paper retrieval for Class 10
    val mathPaper = repo.getPaper(10, "गणित", 2025)
    assertNotNull(mathPaper)
    assertTrue(mathPaper.questions.isNotEmpty())

    // 3. Perform Annual Rollover (simulate 2026 exam completion)
    val result = repo.performAnnualRollover(2026)
    assertEquals(2026, result.newExamYearAdded)
    assertEquals(2016, result.oldestYearDeleted)

    val updatedYears = repo.getActiveYears()
    assertEquals(10, updatedYears.size)
    assertEquals(2026, updatedYears.first())
    assertEquals(2017, updatedYears.last())
    assertFalse(updatedYears.contains(2016))
  }
}
