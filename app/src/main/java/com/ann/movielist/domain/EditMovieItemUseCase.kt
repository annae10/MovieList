package com.ann.movielist.domain

class EditMovieItemUseCase (private val movieListRepository: MovieListRepository) {
    suspend fun editMovieItem(movieItem: MovieItem){
        movieListRepository.editMovieItem(movieItem)
    }
}