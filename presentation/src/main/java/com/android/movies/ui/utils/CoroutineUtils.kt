package com.android.movies.ui.utils

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

// Sequential code
// val result1 = asyncAwait { ...do something asynchronous... }
// val result2 = asyncAwait { ...do something asynchronous... }
// processResults(result1, result2)

// Parallel code
// val result1 = async { ...do something asynchronous... }
// val result2 = async { ...do something asynchronous... }
// processResults(result1.await(), result2.await())

// HOW TO USE IT:
// fun myCoroutine() {
//    launchAsync {
//        try {
//            val result = asyncAwait { ...asynchronous operations...}
//            myProcessingMethod(result)
//        } catch (e: MyException) {
//            ...deal with the exception...
//        }
//    }
// }

fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(UI) { block() }
}

suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return async(CommonPool) { block() }
}

suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
    return async(block).await()
}