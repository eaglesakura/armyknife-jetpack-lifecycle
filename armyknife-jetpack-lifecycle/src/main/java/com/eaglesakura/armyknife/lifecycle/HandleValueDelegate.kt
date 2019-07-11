package com.eaglesakura.armyknife.lifecycle

import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KProperty

class HandleValueDelegate<T> internal constructor(
    private val handle: () -> SavedStateHandle,
    private val key: String,
    private val defValue: T
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = handle().get(key) ?: defValue

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        handle().set(key, value)
    }
}