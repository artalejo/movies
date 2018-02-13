package com.android.movies.ui.base

import android.content.Context
import com.android.movies.R
import com.android.movies.dependencyinjection.qualifier.ApplicationContext
import com.android.movies.model.exceptions.NetworkException
import com.android.movies.ui.exception.ExceptionHandler
import dagger.Reusable
import java.io.FileNotFoundException
import javax.inject.Inject

@Reusable
class AndroidExceptionHandler @Inject constructor(@ApplicationContext val context: Context) : ExceptionHandler {

    override fun<T: ErrorBaseView> notifyException(view: T, exception: Exception?) {
        when (exception) {
            is NetworkException.UnauthorizedException -> view.showUnauthorizedException()
            is NetworkException.NoInternetConnection -> view.showException( context.getString(R.string.no_internet_exception))
            is NetworkException.ServerException -> view.showServerError()
            is NetworkException.UnprocessableEntityException -> view.showException(getUnprocessableEntityMessage(exception.errorMessage!!))
            is FileNotFoundException -> view.showException(context.getString(R.string.file_not_found_exception))
            else -> view.showServerError()
        }
    }

    private fun getUnprocessableEntityMessage(msg: String?) : String {
        if (msg.isNullOrBlank()) return context.getString(R.string.unprocessable_entity_exception)
        return msg!!
    }
}