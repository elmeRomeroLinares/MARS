package com.example.mars

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mars.model.Photo
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.android.synthetic.main.bottom_dialog.view.*

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initView(intent.getParcelableArrayListExtra<Photo>(MainActivity.DATA))
    }

    private fun initView(mData: ArrayList<Photo>?) {
        detailRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailRecyclerView.adapter = mData?.let { photos ->
            DetailRecyclerAdapter(photos) {
                val bottomSheetDialog = BottomSheetDialog()
                val bundle = Bundle()
                bundle.putParcelable("TRY", photos[it])
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(supportFragmentManager, "BottomSheet")
            }
        }
        val snapHelper: SnapHelper = LinearSnapHelper()
        detailRecyclerView.scrollToPosition(intent.getIntExtra(MainActivity.POSITION,0))
        snapHelper.attachToRecyclerView(detailRecyclerView)
    }
}