package com.example.mars.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("landing_date") val landingDate: String?,
    @SerializedName("launch_date") val launchDate: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(landingDate)
        writeString(launchDate)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rover> = object : Parcelable.Creator<Rover> {
            override fun createFromParcel(source: Parcel): Rover = Rover(source)
            override fun newArray(size: Int): Array<Rover?> = arrayOfNulls(size)
        }
    }
}