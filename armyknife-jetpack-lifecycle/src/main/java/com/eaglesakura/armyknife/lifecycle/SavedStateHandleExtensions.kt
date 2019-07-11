package com.eaglesakura.armyknife.lifecycle

import androidx.lifecycle.SavedStateHandle

/**
 * make accessor to simple value.
 *
 * e.g.)
 *
 * class ExampleViewModel(val handle: SavedStateHandle): ViewModel() {
 *      val url: String by handle.delegateValue("url", "https://example.com")
 * }
 */
fun <T> SavedStateHandle.delegateValue(key: String, defValue: T) =
    HandleValueDelegate({ this }, key, defValue)
