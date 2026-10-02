package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.InitialData
import org.junit.Assert.assertEquals
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
    assertEquals("JEE Tracker", appName)
  }

  @Test
  fun `verify syllabus chapters count`() {
    val physics = InitialData.getPhysicsChapters()
    val chemistry = InitialData.getChemistryChapters()
    val math = InitialData.getMathematicsChapters()
    assertEquals(29, physics.size)
    assertEquals(22, chemistry.size)
    assertEquals(28, math.size)
    assertEquals(79, InitialData.getAllChapters().size)
  }

  @Test
  fun `verify mock tests count`() {
    val mockTests = InitialData.getInitialMockTests()
    assertEquals(32, mockTests.size)
  }
}
