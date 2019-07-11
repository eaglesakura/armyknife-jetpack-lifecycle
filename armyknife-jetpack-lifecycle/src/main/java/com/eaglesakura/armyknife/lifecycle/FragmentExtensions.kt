package com.eaglesakura.armyknife.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import com.eaglesakura.armyknife.android.extensions.savedStateHandle

/**
 * make accessor to simple value.
 *
 * e.g.)
 *
 * class ExampleFragment: Fragment() {
 *      val url: String by handle.delegateSavedStateValue("url", "https://example.com")
 * }
 */
fun <T> Fragment.delegateSavedStateValue(
    key: String,
    defValue: T,
    savedStateHandleProvider: () -> SavedStateHandle = { this.savedStateHandle }
) =
    HandleValueDelegate(savedStateHandleProvider, key, defValue)
