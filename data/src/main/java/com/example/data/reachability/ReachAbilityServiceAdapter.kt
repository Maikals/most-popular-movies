package com.example.data.reachability

import com.example.data.net.interceptor.ReachAbilityRequestInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ReachAbilityServiceAdapter(private val reachAbilityRequestInterceptor: ReachAbilityRequestInterceptor) {

    fun createReachAbilityService() : ReachAbilityService {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(reachAbilityRequestInterceptor)
                .connectTimeout(ReachAbilityConstants.TIMEOUT_CONNECTION_VALUE, TimeUnit.SECONDS)
                .readTimeout(ReachAbilityConstants.TIMEOUT_READ_VALUE, TimeUnit.SECONDS)
                .writeTimeout(ReachAbilityConstants.TIMEOUT_WRITE_VALUE, TimeUnit.SECONDS)
        val builder = Retrofit.Builder()
                .baseUrl(ReachAbilityConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.client(httpClient.build()).build().create(ReachAbilityService::class.java)
    }

}