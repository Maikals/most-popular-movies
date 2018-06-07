package com.example.miquelcastanys.cleanlearning.injector.module

import com.example.data.net.ApiConstants
import com.example.data.net.SearchMoviesService
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.repository.SearchMoviesRepositoryImpl
import com.example.data.repository.dataSource.SearchMoviesDataStore
import com.example.data.repository.dataSource.SearchMoviesDataStoreImpl
import com.example.domain.repository.SearchMoviesRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class SearchMovieApiModule {

    @Provides
    @Singleton
    fun provideSearchMovieDataStore(searchMoviesDataStore: SearchMoviesDataStoreImpl): SearchMoviesDataStore {
        return searchMoviesDataStore
    }

    @Provides
    @Singleton
    fun provideSearchMovieRepository(searchMoviesRepositoryImpl: SearchMoviesRepositoryImpl): SearchMoviesRepository {
        return searchMoviesRepositoryImpl
    }

    @Provides
    @Singleton
    fun searchMoviesApi(authInterceptor: RequestInterceptor): SearchMoviesService {
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
        return builder.client(httpClient.build()).build().create(SearchMoviesService::class.java)
    }
}