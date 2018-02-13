package com.android.movies.dependencyinjection


import com.android.movies.dependencyinjection.qualifier.DefaultQueries
import com.android.movies.network.ApiEndpoints
import com.android.movies.network.interceptors.AuthenticationInterceptor
import com.android.movies.repository.PreferencesRepository
import com.android.movies.repository.datasource.CacheDataSource
import com.android.movies.repository.preferences.PreferencesDataRepository
import com.android.movies.repository.preferences.SharedPreferencesDataSource
import com.android.movies.repository.query.Query
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @ElementsIntoSet
    @Singleton
    @DefaultQueries
    fun provideDefaultQueries(): MutableSet<Query> {
        return LinkedHashSet()
    }

    @Provides
    @Singleton
    fun providesRetrofit(authenticationInterceptor: AuthenticationInterceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                .build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiEndpoints.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesSharedPreferencesDataSource(sharedPreferencesDataSource: SharedPreferencesDataSource)
            : CacheDataSource<String, String>
    {
        return sharedPreferencesDataSource
    }

    @Provides
    @Singleton
    fun providesPreferencesDataRepository(preferencesDataRepository: PreferencesDataRepository)
            : PreferencesRepository {
        return preferencesDataRepository
    }

}
