package com.example.mars

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mars.model.Photo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog.view.*

class BottomSheetDialog: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = this.arguments?.getParcelable<Photo>("TRY")

        view.camera_TV.text = data?.camera?.name
        view.landing_TV.text = "Landing: " + data?.rover?.landingDate
        view.launching_TV.text = "Launching: " + data?.rover?.launchDate
        view.share_BTN.setOnClickListener {
            shareTextUrl(data)
        }
    }

    private fun shareTextUrl(data: Photo?) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        share.putExtra(Intent.EXTRA_TEXT, data?.imageSrc)

        startActivity(Intent.createChooser(share, "Share link!"))
    }
}