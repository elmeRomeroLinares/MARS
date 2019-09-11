package com.example.mars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mars.glide.GlideApp
import com.example.mars.model.Photo
import kotlinx.android.synthetic.main.detail_item_view.view.*

class DetailRecyclerAdapter (private var mData: ArrayList<Photo>,
                             private val listener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.detail_item_view, parent, false))
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val detailViewHolder = holder as DetailViewHolder
        detailViewHolder.bindView(mData[position], position, listener)
    }
}

class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindView(photoModel: Photo, pos: Int, listener: (Int) -> Unit) {
        GlideApp.with(itemView.context).load(photoModel.imageSrc).diskCacheStrategy(DiskCacheStrategy.ALL).into(itemView.detail_IV)

        itemView.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                listener(pos)
                return true
            }
        })
    }
}