package com.eaglesakura.armyknife.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeActivity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityExtensionsKtTest {

    @Test
    fun delegateSavedStateValue() = compatibleBlockingTest {
        val activity = makeActivity(ActivityExtensionKtTestActivity::class)

        assertEquals("", activity.url)
        activity.url = "https://example.com"
        assertEquals("https://example.com", activity.url)
    }
}

internal class ActivityExtensionKtTestActivity : AppCompatActivity() {
    var url: String by delegateSavedStateValue("url", "")
}