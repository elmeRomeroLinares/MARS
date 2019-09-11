package com.example.mars.network

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {

    companion object{
        private const val BASE_URL: String = "https://api.nasa.gov/mars-photos/api/v1/"
    }


    fun getRetrofitInstance(cache: Cache, networkState: Boolean): GetMarsPhotos{
        val provideClient = OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(CacheInterceptor(networkState))
            .addInterceptor(QueryInterceptor())
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            client(provideClient).
            build()

        return retrofit.create(GetMarsPhotos::class.java)
    }
}