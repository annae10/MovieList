package com.ann.movielist.domain

class DeleteMovieItemUseCase (private val movieListRepository: MovieListRepository) {
    suspend fun deleteMovieItem(movieItem: MovieItem){
        movieListRepository.deleteMovieItem(movieItem)
    }
}