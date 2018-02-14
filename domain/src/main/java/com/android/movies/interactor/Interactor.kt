package com.android.movies.interactor

import com.android.movies.Result
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import kotlin.coroutines.experimental.AbstractCoroutineContextElement

abstract class Interactor<out SuccessValue, in Parameters> {

    @Inject lateinit var androidContext: AbstractCoroutineContextElement
    var job: Job? = null

    fun execute(parameters: Parameters, delegate: (result: Result<SuccessValue, *>) -> Unit) {
        job = launch(androidContext) {
            val result = async {
                run(parameters)
            }
            delegate(result.await())
        }
    }

    abstract fun run(params: Parameters): Result<SuccessValue, *>
    fun cancel() = job?.cancel()
}