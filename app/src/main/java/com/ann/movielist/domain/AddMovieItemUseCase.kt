package com.ann.movielist.domain

class AddMovieItemUseCase (private val movieListRepository: MovieListRepository) {
    suspend fun addMovieItem(movieItem: MovieItem){
        movieListRepository.addMovieItem(movieItem)
    }
}