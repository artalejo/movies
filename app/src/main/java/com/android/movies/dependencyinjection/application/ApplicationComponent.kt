package com.android.movies.dependencyinjection.application

import android.app.Application
import com.android.movies.BaseApplication
import com.android.movies.dependencyinjection.ActivityInjector
import com.android.movies.dependencyinjection.DataModule
import com.android.movies.dependencyinjection.fragment.FragmentInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AndroidInjectionModule::class,
        ActivityInjector::class, FragmentInjector::class, DataModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}