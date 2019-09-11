package com.example.mars.network

import com.example.mars.model.PhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetMarsPhotos {

    @GET("rovers/{vehicle}/photos")
    fun getPhotos(@Path("vehicle") roverVehicle: String = "curiosity", @Query("page") page: String): Call<PhotosResponse>
}