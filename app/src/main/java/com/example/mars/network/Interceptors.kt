package com.example.mars.network

import com.example.mars.MainActivity
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor: Interceptor {

    companion object{
        private const val SOL: String = "sol"
        private const val SOL_VALUE: String = "50"

        private const val API_KEY: String = "api_key"
        private const val API_KEY_VALUE: String = "DEMO_KEY"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalHttpUrl = request.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(SOL, SOL_VALUE)
            .addQueryParameter(API_KEY, API_KEY_VALUE)
            .build()

        val requestBuilder = request.newBuilder().url(url)

        val updateRequest = requestBuilder.build()
        return chain.proceed(updateRequest)
    }
}

class CacheInterceptor(val isNetworkConnected: Boolean): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        request = if (isNetworkConnected)
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

        return chain.proceed(request)
    }

}