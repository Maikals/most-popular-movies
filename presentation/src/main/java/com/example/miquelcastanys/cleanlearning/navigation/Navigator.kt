package com.example.miquelcastanys.cleanlearning.navigation

import MovieDetailActivity
import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.miquelcastanys.cleanlearning.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    fun toMovieDetail(context: Context, movieId: String, view: View) =
            context.startActivity(MovieDetailActivity.newIntent(context, movieId),
                    createSharedTransition(context, view, R.string.transition_string).toBundle())

    private fun createSharedTransition(context: Context, view: View, @StringRes transitionStringId: Int): ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation((context as? AppCompatActivity)!!,
                    view, // Starting view
                    context.getString(transitionStringId)!! // The String
            )
}