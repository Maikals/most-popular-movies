package com.example.miquelcastanys.cleanlearning.view.base

import android.support.v7.widget.Toolbar


interface BaseActivityFragmentInterface {
    fun getToolbar(): Toolbar

    fun isInternetReachable(): Boolean
}