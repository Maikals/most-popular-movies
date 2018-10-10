package com.example.miquelcastanys.cleanlearning

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented d, which will createService on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under d.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.miquelcastanys.cleanlearning", appContext.packageName)
    }
}
