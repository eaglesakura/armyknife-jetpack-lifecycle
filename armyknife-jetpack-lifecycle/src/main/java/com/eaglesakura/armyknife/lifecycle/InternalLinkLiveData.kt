package com.eaglesakura.armyknife.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Sync LiveData in LiveData
 *
 * e.g.
 *
 * class ExampleRepository {
 *      val url: LiveData<String> = ...
 * }
 *
 * // Observe ExampleRepository::url(copied data)
 * val exampleRepository: LiveData<ExampleRepository>
 * val url: LiveData<String> = InternalLinkLiveData(exampleRepository, { it.url })
 */
open class InternalLinkLiveData<T, S>(
    private val source: LiveData<S>,
    private val getLiveDataFromSource: (source: S?) -> LiveData<T>?
) : LiveData<T>() {

    internal var internalSource: LiveData<T>? = null

    override fun onActive() {
        super.onActive()
        source.observeForever(sourceObserver)
    }

    override fun onInactive() {
        source.removeObserver(sourceObserver)
        internalSource?.removeObserver(dataObserver)
        internalSource = null
        super.onInactive()
    }

    private val sourceObserver = Observer<S> {
        val data = getLiveDataFromSource(it)
        if (data == internalSource) {
            // not changed.
            return@Observer
        }

        // reset observer.
        this.internalSource?.removeObserver(dataObserver)

        when (data) {
            null -> {
                this.internalSource = null
            }
            else -> {
                this.internalSource = data
                this.internalSource!!.observeForever(dataObserver)
            }
        }

        onInternalSourceChanged(internalSource)
    }

    /**
     * Notify internal source change message.
     */
    protected open fun onInternalSourceChanged(internalSource: LiveData<T>?) {
        this.value = internalSource?.value
    }

    private val dataObserver = Observer<T> {
        this.value = it
    }
}