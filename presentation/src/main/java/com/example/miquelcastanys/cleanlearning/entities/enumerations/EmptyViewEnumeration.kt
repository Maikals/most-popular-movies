package com.example.miquelcastanys.cleanlearning.entities.enumerations

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.example.miquelcastanys.cleanlearning.R

enum class EmptyViewEnumeration(@DrawableRes val imageId: Int, @StringRes val title: Int, @StringRes val subtitle: Int) {
    MOVIES_LIST_EMPTY_VIEW(R.drawable.ic_empty_view_black, R.string.error_title_no_movies, R.string.error_description_no_movies)
}