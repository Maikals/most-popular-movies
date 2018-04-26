package com.example.data.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor() : Interceptor {



    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        var url = request?.url()

        val timeStamp = System.currentTimeMillis()

        val urlBuilder = url?.newBuilder()
        //put common queries

        url = urlBuilder?.build()
        request = request?.newBuilder()?.url(url)?.build()
        println("URl -> " + request?.url())
        return chain?.proceed(request)!!
    }
}