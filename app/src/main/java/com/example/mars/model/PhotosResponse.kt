package com.example.mars.model

import com.google.gson.annotations.SerializedName

class PhotosResponse (
    @SerializedName("photos") private val _photos: ArrayList<Photo>
) {
    val photos
        get() = _photos
}