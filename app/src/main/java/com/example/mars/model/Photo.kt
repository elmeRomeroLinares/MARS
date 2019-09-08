package com.example.mars.model

import com.google.gson.annotations.SerializedName

class Photo (
    @SerializedName("camera") val camera: Camera,
    @SerializedName("img_src") val imageSrc: String?,
    @SerializedName("rover")private val rover: Rover
)