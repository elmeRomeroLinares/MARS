package com.example.mars

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mars.glide.GlideApp
import com.example.mars.model.Photo
import kotlinx.android.synthetic.main.item_list_grid_photo.view.*

class GridRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mData = ArrayList<Photo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NormalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_grid_photo, parent, false))
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val normalViewHolder = holder as NormalViewHolder
        normalViewHolder.bindView(mData[position])
    }

    fun setPhotosArrayList(arrayListOfPhotos: ArrayList<Photo>){
        this.mData = arrayListOfPhotos
        notifyDataSetChanged()
    }
}

class NormalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bindView(photoModel: Photo) {
        GlideApp.with(itemView.context).load(photoModel.imageSrc).into(itemView.itemIV)
    }
}

class GridItemDecoration(gridSpacingPx: Int, gridSize: Int): RecyclerView.ItemDecoration(){
    private var mSizeGridSpacingPx: Int = gridSpacingPx
    private var mGridSize: Int = gridSize

    private var mNeedLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth = ((parent.width - mSizeGridSpacingPx.toFloat() * (mGridSize - 1)) / mGridSize).toInt()
        val padding = parent.width / mGridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < mGridSize) {
            outRect.top = 0
        } else {
            outRect.top = mSizeGridSpacingPx
        }
        if (itemPosition % mGridSize == 0) {
            outRect.left = 0
            outRect.right = padding
            mNeedLeftSpacing = true
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx - padding
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mSizeGridSpacingPx - padding
            } else {
                outRect.right = mSizeGridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = mSizeGridSpacingPx - padding
        } else {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = mSizeGridSpacingPx / 2
        }
        outRect.bottom = 0
    }
}