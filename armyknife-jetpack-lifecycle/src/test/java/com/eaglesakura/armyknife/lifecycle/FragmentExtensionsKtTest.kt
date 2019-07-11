package com.eaglesakura.armyknife.lifecycle

import androidx.fragment.app.Fragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.awaitOnCreate
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentExtensionsKtTest {

    @Test
    fun delegateSavedStateValue() = compatibleBlockingTest {
        val fragment = makeFragment(FragmentExtensionKtTestFragment::class).awaitOnCreate()
        assertEquals("", fragment.url)
        fragment.url = "https://example.com"
        assertEquals("https://example.com", fragment.url)
    }
}

internal class FragmentExtensionKtTestFragment : Fragment() {
    var url: String by delegateSavedStateValue("url", "")
}
