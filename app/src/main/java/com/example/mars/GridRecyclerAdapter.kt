package com.example.mars

import android.graphics.Rect
import android.transition.CircularPropagation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mars.glide.GlideApp
import com.example.mars.model.Photo
import kotlinx.android.synthetic.main.item_list_grid_photo.view.*

class GridRecyclerAdapter(private var mData: ArrayList<Photo>,
                          private val listener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NormalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_grid_photo, parent, false))
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val normalViewHolder = holder as NormalViewHolder
        normalViewHolder.bindView(mData[position], position, listener)
    }

    fun addItems(items: ArrayList<Photo>) {
        mData.addAll(items)
        notifyDataSetChanged()
    }
}

class NormalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bindView(photoModel: Photo, pos: Int, listener: (Int) -> Unit) {

        val circularProgressDrawable = CircularProgressDrawable(itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        GlideApp.with(itemView.context)
            .load(photoModel.imageSrc)
            .placeholder(circularProgressDrawable).into(itemView.itemIV)


        itemView.setOnClickListener {
            listener(pos)
        }
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
        when {
            (itemPosition % mGridSize == 0) -> {
                outRect.left = 0
                outRect.right = padding
                mNeedLeftSpacing = true
            }
            ((itemPosition + 1) % mGridSize == 0) -> {
                mNeedLeftSpacing = false
                outRect.right = 0
                outRect.left = padding
            }
            (mNeedLeftSpacing) -> {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx - padding
                if ((itemPosition + 2) % mGridSize == 0) {
                    outRect.right = mSizeGridSpacingPx - padding
                } else {
                    outRect.right = mSizeGridSpacingPx / 2
                }
            }
            ((itemPosition + 2) % mGridSize == 0) -> {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx / 2
                outRect.right = mSizeGridSpacingPx - padding
            }
            else -> {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx / 2
                outRect.right = mSizeGridSpacingPx / 2
            }
        }

        outRect.bottom = 0
    }
}