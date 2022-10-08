package com.ann.movielist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ann.movielist.domain.MovieItem
import com.ann.movielist.domain.MovieListRepository

class MovieListRepositoryImpl (application: Application) : MovieListRepository {

    private val movieListDao = AppDatabase.getInstance(application).movieListDao()
    private val mapper = MovieListMapper()

    override suspend fun addMovieItem(movieItem: MovieItem) {
        movieListDao.addMovieItem(mapper.mapEntityToDbModel(movieItem))
    }

    override suspend fun deleteMovieItem(movieItem: MovieItem) {
        movieListDao.deleteMovieItem(movieItem.id)
    }

    override suspend fun editMovieItem(movieItem: MovieItem) {
        movieListDao.addMovieItem(mapper.mapEntityToDbModel(movieItem))
    }

    override suspend fun getMovieItem(movieItemId: Int): MovieItem {
        val dbModel = movieListDao.getMovieItem(movieItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getMovieList(): LiveData<List<MovieItem>> = Transformations.map(
        movieListDao.getMovieList()
    ){
        mapper.mapListDbModelToListEntity(it)
    }
}