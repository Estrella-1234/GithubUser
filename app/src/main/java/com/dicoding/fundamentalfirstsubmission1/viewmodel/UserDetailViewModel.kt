package com.dicoding.fundamentalfirstsubmission1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.fundamentalfirstsubmission1.data.local.FavoriteUserDao
import com.dicoding.fundamentalfirstsubmission1.data.local.UserDatabase
import com.dicoding.fundamentalfirstsubmission1.data.retrofit.ApiConfig
import com.dicoding.fundamentalfirstsubmission1.models.DetailResponse
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {
    private  var favoriteDao: FavoriteUserDao?
    private var favoriteDb: UserDatabase?

    init {
        favoriteDb = UserDatabase.getDatabase(application)
        favoriteDao = favoriteDb?.favoriteUserDao()
    }

    fun addFavorite(login: String, id: Int, avatarUrl: String, htmlUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = GithubUser(
                avatarUrl,
                htmlUrl,
                login,
                id

            )
            favoriteDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favoriteDao?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFavorite(id)
        }
    }


    private val _listDetail = MutableLiveData<DetailResponse>()
    val listDetail: LiveData<DetailResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    internal fun getGithubUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDetailModel"
    }

}