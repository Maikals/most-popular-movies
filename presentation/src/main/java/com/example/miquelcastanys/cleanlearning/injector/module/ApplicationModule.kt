package com.example.miquelcastanys.cleanlearning.injector.module

import android.content.Context
import com.example.data.reachability.ReachAbilityConstants
import com.example.data.reachability.ReachAbilityDevicesImpl
import com.example.data.reachability.ReachAbilityImpl
import com.example.data.reachability.ReachAbilityService
import com.example.data.repository.dataSource.ReachAbilityDataStore
import com.example.data.repository.dataSource.ReachAbilityDataStoreImpl
import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.ReachAbility
import com.example.domain.repository.ReachAbilityDevices
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.UIThread
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MostPopularMoviesApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideUIThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @Singleton
    fun provideReachAbilityDataStore(reachAbilityDataStoreImpl: ReachAbilityDataStoreImpl): ReachAbilityDataStore = reachAbilityDataStoreImpl

    @Provides
    @Singleton
    fun provideReachabilityRepository(reachAbilityImpl: ReachAbilityImpl): ReachAbility = reachAbilityImpl

    @Provides
    @Singleton
    fun reachAbilityModuleProvider(): ReachAbilityService {
        val httpClient = OkHttpClient.Builder()
                //.addInterceptor(authInterceptor)
                .connectTimeout(ReachAbilityConstants.TIMEOUT_CONNECTION_VALUE, TimeUnit.SECONDS)
                .readTimeout(ReachAbilityConstants.TIMEOUT_READ_VALUE, TimeUnit.SECONDS)
                .writeTimeout(ReachAbilityConstants.TIMEOUT_WRITE_VALUE, TimeUnit.SECONDS)
        val builder = Retrofit.Builder()
                .baseUrl(ReachAbilityConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.client(httpClient.build()).build().create(ReachAbilityService::class.java)
    }

    @Provides
    @Singleton
    fun providesReachAbilityDevices(reachAbilityDevicesImpl: ReachAbilityDevicesImpl): ReachAbilityDevices = reachAbilityDevicesImpl

}