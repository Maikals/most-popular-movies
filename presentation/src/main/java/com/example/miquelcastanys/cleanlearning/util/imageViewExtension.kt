package com.example.miquelcastanys.cleanlearning.util

import android.widget.ImageView
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.glideModule.GlideApp

fun ImageView.loadImage(imageUrl: String) =
        GlideApp
                .with(context)
                .load("${Constants.BASE_IMAGE_URL}$imageUrl")
                .placeholder(R.drawable.ic_empty_view_white)
                .centerCrop()
                .into(this)
