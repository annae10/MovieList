package com.ann.movielist.domain

class GetMovieItemUseCase (private val movieListRepository: MovieListRepository){
    suspend fun getMovieItem(movieItemId: Int): MovieItem{
        return movieListRepository.getMovieItem(movieItemId)
    }
}