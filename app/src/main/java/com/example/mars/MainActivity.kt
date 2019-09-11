package com.example.mars

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mars.CoreApp.Companion.instance
import com.example.mars.model.PhotosResponse
import com.example.mars.network.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    private lateinit var photosListAdapter: GridRecyclerAdapter

    companion object {
        const val DATA: String = "DATA"
        const val POSITION: String = "POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        var pageNumber = 1

        main_progress.visibility = View.VISIBLE

        mainRecyclerView.layoutManager = GridLayoutManager(this,2)
        mainRecyclerView.addItemDecoration(GridItemDecoration(10,2))

        mainRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            var isScrolling = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItems = (mainRecyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (mainRecyclerView.layoutManager as LinearLayoutManager).itemCount
                val firstItem = (mainRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + firstItem == totalItemCount)) {
                    isScrolling = false

                    pageNumber++
                    loadFromApi(pageNumber)
                }
            }
        })

        loadFromApi(pageNumber)
    }

    private fun loadFromApi(page: Int){

        val call = instance.getRetrofitInstance()?.getPhotos(page = page.toString())

        call?.enqueue(object: Callback<PhotosResponse>{
            override fun onFailure(call: Call<PhotosResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,"An error has occurred, please try again later", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PhotosResponse>, response: retrofit2.Response<PhotosResponse>) {
                if (response.isSuccessful) {
                    main_progress.visibility = View.GONE

                    val data = response.body()
                    bindData(data)
                }
            }
        })
    }

    private fun bindData(data: PhotosResponse?) {

        if (mainRecyclerView.adapter == null) {
            data?.photos?.let {photos ->
                photosListAdapter =
                    GridRecyclerAdapter(photos) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra(POSITION, it)
                        intent.putParcelableArrayListExtra(DATA, photos)
                        startActivity(intent)
                    }
            }
            mainRecyclerView.adapter = photosListAdapter
        } else {
            data?.photos?.let { photosListAdapter.addItems(it) }
            Log.d("Try", "Time to load more data")
        }
    }
}
