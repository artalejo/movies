package com.android.movies.ui.base

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor

class AndroidThreadContinuation<T>(private val continuation: Continuation<T>): Continuation<T> by continuation {
    override fun resume(value: T) {
        if (Looper.myLooper() == Looper.getMainLooper()) continuation.resume(value)
        else Handler(Looper.getMainLooper()).post { continuation.resume(value) }
    }

    override fun resumeWithException(exception: Throwable) {
        if (Looper.myLooper() == Looper.getMainLooper()) continuation.resumeWithException(exception)
        else Handler(Looper.getMainLooper()).post { continuation.resumeWithException(exception) }
    }
}

object Android : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            AndroidThreadContinuation(continuation)
}

object TestContextProvider {
    // ContextElement passed to the test so that the coroutines get executed in the same thread.
    val TestContext: AbstractCoroutineContextElement = Unconfined
}