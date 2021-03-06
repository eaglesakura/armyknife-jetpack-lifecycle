package com.eaglesakura.armyknife.lifecycle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeActivityViewModel
import com.eaglesakura.armyknife.android.junit4.extensions.targetApplication
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SavedStateHandleExtensionsKtTest {
    @Test
    fun delegateValue() = compatibleBlockingTest(Dispatchers.Main) {
        val viewModel = makeActivityViewModel { activity ->
            ViewModelProvider(activity, SavedStateViewModelFactory(targetApplication, activity))
                .get(ExampleViewModel::class.java)
        }

        assertNull(viewModel.handle.get("url"))
        assertEquals("https://example.com", viewModel.url)
        viewModel.url = "https://example.com/write"
        assertEquals("https://example.com/write", viewModel.url)
        assertEquals("https://example.com/write", viewModel.handle.get<String>("url"))
    }

    class ExampleViewModel(val handle: SavedStateHandle) : ViewModel() {
        var url: String by handle.delegateValue("url", "https://example.com")
    }
}