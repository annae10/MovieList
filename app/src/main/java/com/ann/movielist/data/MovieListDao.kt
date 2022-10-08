package com.ann.movielist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieListDao {

    @Query("SELECT * FROM movie_items")
    fun getMovieList() : LiveData<List<MovieItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieItem(movieItemDbModel: MovieItemDbModel)

    @Query("DELETE FROM movie_items WHERE id=:movieItemId")
    suspend fun deleteMovieItem(movieItemId: Int)

    @Query("SELECT * FROM movie_items WHERE id=:movieItemId LIMIT 1")
    suspend fun getMovieItem(movieItemId: Int): MovieItemDbModel
}