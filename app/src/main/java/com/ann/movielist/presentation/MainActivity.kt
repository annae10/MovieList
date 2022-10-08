package com.ann.movielist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ann.movielist.R
import com.ann.movielist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MovieItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private var movieItemContainer: FragmentContainerView? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieItemContainer = findViewById(R.id.movie_item_container)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movieList.observe(this) {
            movieListAdapter.submitList(it)
        }
        binding.buttonAddMovieItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = MovieItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(MovieItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onEditingFinished(){
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.movieItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        with(binding.rvMovieList) {
            movieListAdapter = MovieListAdapter()
            adapter = movieListAdapter
            recycledViewPool.setMaxRecycledViews(
                MovieListAdapter.VIEW_TYPE_ENABLED,
                MovieListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                MovieListAdapter.VIEW_TYPE_DISABLED,
                MovieListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.rvMovieList)
    }

    private fun setupSwipeListener(rvTaskList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = movieListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTaskItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvTaskList)
    }

    private fun setupClickListener() {
        movieListAdapter.onMovieItemClickListener = {
            if (isOnePaneMode()) {
                val intent = MovieItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(MovieItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        movieListAdapter.onMovieItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}