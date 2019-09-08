package com.example.mars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mars.model.PhotosResponse
import com.example.mars.network.GetMarsPhotos
import com.example.mars.network.QueryInterceptor
import com.example.mars.network.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mainRecyclerView.layoutManager = GridLayoutManager(this,2)
        mainRecyclerView.addItemDecoration(GridItemDecoration(10,2))

        val photosListAdapter = GridRecyclerAdapter {
            Toast.makeText(this, "The item press was $it" ,Toast.LENGTH_SHORT).show()
        }

        val call = RetrofitClientInstance().getRetrofitInstance().getPhotos("curiosity","1")

        call.enqueue(object: Callback<PhotosResponse>{
            override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,"An error has occurred, please try again later", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PhotosResponse>, response: retrofit2.Response<PhotosResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity,"Success", Toast.LENGTH_SHORT).show()

                    val data = response.body()
                    mainRecyclerView.adapter = photosListAdapter
                    photosListAdapter.setPhotosArrayList(data!!.photos)
                }
            }
        })
    }
}
