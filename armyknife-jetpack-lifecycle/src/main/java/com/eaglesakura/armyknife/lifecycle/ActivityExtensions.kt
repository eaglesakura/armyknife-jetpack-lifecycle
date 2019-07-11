package com.eaglesakura.armyknife.lifecycle

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateHandle
import com.eaglesakura.armyknife.android.extensions.savedStateHandle

/**
 * make accessor to simple value.
 *
 * e.g.)
 *
 * class ExampleActivity: AppCompatActivity() {
 *      val url: String by handle.delegateSavedStateValue("url", "https://example.com")
 * }
 */
fun <T> FragmentActivity.delegateSavedStateValue(
    key: String,
    defValue: T,
    savedStateHandleProvider: () -> SavedStateHandle = { this.savedStateHandle }
) =
    HandleValueDelegate(savedStateHandleProvider, key, defValue)
