package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppRole
import com.example.ui.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("UMV Gangauli", appName)
  }

  @Test
  fun `test demo student login and library book catalog`() {
    val viewModel = MainViewModel()
    viewModel.fillDemoLogin(AppRole.STUDENT)
    
    val state = viewModel.uiState.value
    assertTrue(state.isStudentLoggedIn)
    assertEquals("अमित कुमार (Amit Kumar)", state.currentStudent.name)
    assertEquals(10, state.currentStudent.studentClass)
    assertEquals(14, state.currentStudent.rollNo)
    assertTrue(state.books.isNotEmpty())
  }
}
