package com.android.movies.dependencyinjection.application

import android.app.Application
import android.content.Context
import com.android.movies.dependencyinjection.qualifier.ApplicationContext
import com.android.movies.ui.base.Android
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.coroutines.experimental.AbstractCoroutineContextElement

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providesContinuation(): AbstractCoroutineContextElement = Android
}