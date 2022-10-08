package com.ann.movielist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ann.movielist.R
import com.ann.movielist.domain.MovieItem
import com.ann.movielist.domain.MovieItem.Companion.UNDEFINED_ID

class MovieItemActivity :  AppCompatActivity(), MovieItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MovieItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var movieItemId = MovieItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_item)
        parseIntent()

        if (savedInstanceState == null){
            launchRightMode()
        }
    }

    override fun onEditingFinished(){
        finish()
    }

    private fun launchRightMode(){
        val fragment = when(screenMode){
            MODE_EDIT -> MovieItemFragment.newInstanceEditItem(movieItemId)
            MODE_ADD -> MovieItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_item_container, fragment)
            .commit()
    }

    private fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if(!intent.hasExtra(EXTRA_MOVIE_ITEM_ID)){
                throw RuntimeException("Param movie item id is absent")
            }
            movieItemId = intent.getIntExtra(EXTRA_MOVIE_ITEM_ID, UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_MOVIE_ITEM_ID = "extra_movie_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, MovieItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, movieItemId: Int): Intent {
            val intent = Intent(context, MovieItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MOVIE_ITEM_ID, movieItemId)
            return intent
        }

    }
}