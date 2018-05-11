package com.example.miquelcastanys.cleanlearning

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflateFromLayout(@LayoutRes layout: Int): View =
        LayoutInflater.from(context).inflate(layout, this, false)
