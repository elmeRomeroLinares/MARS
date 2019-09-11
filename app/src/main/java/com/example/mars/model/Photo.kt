package com.example.mars.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("camera") val camera: Camera?,
    @SerializedName("img_src") val imageSrc: String?,
    @SerializedName("rover") val rover: Rover?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<Camera>(Camera::class.java.classLoader) ,
        source.readString(),
        source.readParcelable<Rover>(Rover::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(camera, 0)
        writeString(imageSrc)
        writeParcelable(rover, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(source: Parcel): Photo = Photo(source)
            override fun newArray(size: Int): Array<Photo?> = arrayOfNulls(size)
        }
    }
}