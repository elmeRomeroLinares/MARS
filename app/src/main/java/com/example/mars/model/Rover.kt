package com.example.mars.model

import com.google.gson.annotations.SerializedName

class Rover (
    @SerializedName("landing_date") private val _landingDate: String?,
    @SerializedName("launch_date") private val _launchDate: String?
) {
    val landingDate
        get() = _landingDate

    val launchDate
        get() = _launchDate
}