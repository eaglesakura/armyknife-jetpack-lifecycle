package com.eaglesakura.armyknife.lifecycle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("MoveLambdaOutsideParentheses")
@RunWith(AndroidJUnit4::class)
class InternalLinkLiveDataTest {

    @Test
    fun copy() = compatibleBlockingTest(Dispatchers.Main) {
        val activity = makeActivity()

        val repository = MutableLiveData<ExampleRepository>()
        val url = InternalLinkLiveData(repository, { it?.url })

        yield()
        assertNull(url.internalSource)
        assertFalse(repository.hasObservers())
        assertFalse(repository.hasActiveObservers())

        url.observe(activity, Observer {
            Log.i("InternalLinkLiveData", "value='$it'")
        })
        yield()
        assertTrue(repository.hasObservers())
        assertTrue(repository.hasActiveObservers())

        repository.value = ExampleRepository()
        yield()
        assertEquals(repository.value?.url, url.internalSource)

        repository.value!!.url.value = "A"
        yield()
        assertEquals("A", url.value)

        repository.value!!.url.value = "B"
        yield()
        assertEquals("B", url.value)

        url.removeObservers(activity)
        yield()
        assertNull(url.internalSource)
        assertFalse(repository.hasObservers())
        assertFalse(repository.hasActiveObservers())
    }

    class ExampleRepository {
        val url = MutableLiveData<String>()
    }
}