package com.example.data.net.interceptor

import com.example.data.BuildConfig
import com.example.domain.base.Log
import okhttp3.Interceptor
import okhttp3.Response

class ReachAbilityRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        var url = request?.url()


        request = request?.newBuilder()?.url(url!!)?.build()
        if (BuildConfig.DEBUG) Log.d("RequestInterceptor", "URl -> " + request?.url())
        return chain?.proceed(request!!)!!
    }
}