package com.ann.movielist.data

import com.ann.movielist.domain.MovieItem

class MovieListMapper {
    fun mapEntityToDbModel(movieItem: MovieItem) = MovieItemDbModel(
        id = movieItem.id,
        title = movieItem.title,
        enabled = movieItem.enabled
    )

    fun mapDbModelToEntity(movieItemDbModel: MovieItemDbModel) = MovieItem(
        id = movieItemDbModel.id,
        title = movieItemDbModel.title,
        enabled = movieItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<MovieItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}