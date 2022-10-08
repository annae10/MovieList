package com.ann.movielist.domain

import androidx.lifecycle.LiveData

class GetMovieListUseCase (private val movieListRepository: MovieListRepository) {
    fun getMovieList(): LiveData<List<MovieItem>> {
        return movieListRepository.getMovieList()
    }
}