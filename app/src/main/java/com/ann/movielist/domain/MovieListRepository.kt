package com.ann.movielist.domain

import androidx.lifecycle.LiveData

interface MovieListRepository {

    suspend fun addMovieItem(movieItem: MovieItem)
    suspend fun deleteMovieItem(movieItem: MovieItem)
    suspend fun editMovieItem(movieItem: MovieItem)
    suspend fun getMovieItem(movieItemId: Int): MovieItem
    fun getMovieList(): LiveData<List<MovieItem>>

}