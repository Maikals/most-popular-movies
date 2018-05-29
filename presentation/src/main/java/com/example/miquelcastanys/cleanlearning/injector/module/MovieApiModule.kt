package com.example.miquelcastanys.cleanlearning.injector.module

import com.example.data.net.ApiConstants
import com.example.data.net.MostPopularMoviesService
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.repository.MostPopularMoviesRepositoryImpl
import com.example.data.repository.dataSource.MostPopularDataStoreImpl
import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.MostPopularMoviesRepository
import com.example.miquelcastanys.cleanlearning.UIThread
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class MovieApiModule {

    @Provides
    @Singleton
    fun provideMostPopularMoviesDataStore(mostPopularDataStoreImpl: MostPopularDataStoreImpl): MostPopularMoviesStore {
        return mostPopularDataStoreImpl
    }

    @Provides
    @Singleton
    fun provideMostPopularMoviesRepository(mostPopularMoviesRepositoryImpl: MostPopularMoviesRepositoryImpl): MostPopularMoviesRepository {
        return mostPopularMoviesRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUIThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @Singleton
    fun provideHeroApi(authInterceptor: RequestInterceptor): MostPopularMoviesService {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(ApiConstants.TIMEOUT_CONNECTION_VALUE, TimeUnit.SECONDS)
                .readTimeout(ApiConstants.TIMEOUT_READ_VALUE, TimeUnit.SECONDS)
                .writeTimeout(ApiConstants.TIMEOUT_WRITE_VALUE, TimeUnit.SECONDS)
        val builder = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        return builder.client(httpClient.build()).build().create(MostPopularMoviesService::class.java)
    }
}