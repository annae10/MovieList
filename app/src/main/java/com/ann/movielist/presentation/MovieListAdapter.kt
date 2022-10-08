package com.ann.movielist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.ann.movielist.R
import com.ann.movielist.databinding.ItemMovieDisabledBinding
import com.ann.movielist.databinding.ItemMovieEnabledBinding
import com.ann.movielist.domain.MovieItem

class MovieListAdapter : ListAdapter<MovieItem, MovieItemViewHolder>(MovieItemDiffCallback()) {

    var onMovieItemLongClickListener: ((MovieItem) -> Unit)? = null
    var onMovieItemClickListener: ((MovieItem)-> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieItemViewHolder {
        val layout = when (viewType){
            VIEW_TYPE_DISABLED -> R.layout.item_movie_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_movie_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), layout, parent, false )
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movieItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onMovieItemLongClickListener?.invoke(movieItem)
            true
        }
        binding.root.setOnClickListener {
            onMovieItemClickListener?.invoke(movieItem)
        }
        when (binding){
            is ItemMovieDisabledBinding -> {
                binding.movieItem = movieItem
            }
            is ItemMovieEnabledBinding -> {
                binding.movieItem = movieItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 111
        const val VIEW_TYPE_DISABLED = 121

        const val MAX_POOL_SIZE = 30
    }

}