package com.example.mars.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {

    companion object{
        private const val BASE_URL: String = "https://api.nasa.gov/mars-photos/api/v1/"
    }


    fun getRetrofitInstance(): GetMarsPhotos{
        val provideClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(QueryInterceptor())
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            client(provideClient).
            build()

        return retrofit.create(GetMarsPhotos::class.java)
    }
}