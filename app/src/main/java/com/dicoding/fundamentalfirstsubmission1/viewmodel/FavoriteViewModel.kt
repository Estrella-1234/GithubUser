package com.dicoding.fundamentalfirstsubmission1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.fundamentalfirstsubmission1.data.local.FavoriteUserDao
import com.dicoding.fundamentalfirstsubmission1.data.local.UserDatabase
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private  var favoriteDao: FavoriteUserDao?
    private var favoriteDb: UserDatabase?

    init {
        favoriteDb = UserDatabase.getDatabase(application)
        favoriteDao = favoriteDb?.favoriteUserDao()
    }

     fun getFavorite(): LiveData<List<GithubUser>>? {
         return favoriteDao?.getFavoriteUser()
     }
}