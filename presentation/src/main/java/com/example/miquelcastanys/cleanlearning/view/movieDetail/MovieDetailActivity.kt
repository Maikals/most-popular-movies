ˇpackage com.example.miquelcastanys.cleanlearning.view.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity

class MovieDetailActivity : BaseActivity() {

    companion object {
        private const val TAG = "MovieDetailActivity"
        private const val EXTRA_MOVIE_ID = "extra_movie_id"

        fun newIntent(context: Context, movieId: String): Intent =
                Intent(context, MovieDetailActivity::class.java).apply {
                    putExtra(EXTRA_MOVIE_ID, movieId)
                }

    }

    override fun createFragmentAndSettingTAG() {
        currentFragment = MovieDetailFragment.newInstance(intent.getStringExtra(EXTRA_MOVIE_ID))
        currentTag = MovieDetailFragment.TAG
    }

    override fun getActivityLayout(): Int = R.layout.activity_movie_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
