package com.evision.Utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.evision.R

class AppDialog(mContext: Context): Dialog(mContext, R.style.loader_them) {
    val mContext=mContext
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.loader)
        val imag=findViewById<ImageView>(R.id.IMG_loader)
        Glide.with(mContext).load(R.drawable.loading).into(imag)
       setCanceledOnTouchOutside(false)
    }
}