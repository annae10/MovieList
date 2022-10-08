package com.ann.movielist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.ann.movielist.domain.MovieItem

class MovieItemDiffCallback : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }

}