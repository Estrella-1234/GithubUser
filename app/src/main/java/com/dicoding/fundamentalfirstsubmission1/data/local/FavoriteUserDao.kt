package com.dicoding.fundamentalfirstsubmission1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser


@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser: GithubUser)

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUser(): LiveData<List<GithubUser>>

    @Query("SELECT count(*) FROM favorite_users WHERE favorite_users.id = :id")
    fun checkUser(id: Int): Int


    @Query("DELETE FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun removeFavorite(id: Int): Int

}