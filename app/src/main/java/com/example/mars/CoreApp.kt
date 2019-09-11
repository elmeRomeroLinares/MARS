package com.example.mars

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.mars.network.GetMarsPhotos
import com.example.mars.network.RetrofitClientInstance
import okhttp3.Cache

class CoreApp : Application() {

    companion object {
        lateinit var instance: CoreApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun initCache(): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(instance.cacheDir, cacheSize)
    }

    fun getRetrofitInstance(): GetMarsPhotos? {
        return RetrofitClientInstance().getRetrofitInstance(initCache(), hasNetwork())
    }

    private fun hasNetwork(): Boolean {

        val connectivityManager = instance.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = connectivityManager.activeNetworkInfo

            networkInfo?.let{
                return networkInfo.isConnected() && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
            }

        } else {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

            if (networkCapabilities != null) {
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }

        return false
    }

}