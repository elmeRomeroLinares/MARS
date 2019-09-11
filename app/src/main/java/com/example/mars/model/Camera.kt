package com.example.mars.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Camera(
    @SerializedName("name") val name: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Camera> = object : Parcelable.Creator<Camera> {
            override fun createFromParcel(source: Parcel): Camera = Camera(source)
            override fun newArray(size: Int): Array<Camera?> = arrayOfNulls(size)
        }
    }
}


