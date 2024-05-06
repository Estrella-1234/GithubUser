package com.dicoding.fundamentalfirstsubmission1.data.retrofit


import com.dicoding.fundamentalfirstsubmission1.BuildConfig
import com.dicoding.fundamentalfirstsubmission1.models.DetailResponse
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser
import com.dicoding.fundamentalfirstsubmission1.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GHP_TOKEN}")
    fun searchUser(
        @Query("q") login: String
    ): Call<UserResponse>

    @GET("users/{login}")
    @Headers("Authorization: token ${BuildConfig.GHP_TOKEN}")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<DetailResponse>

    @GET("users/{login}/followers")
    @Headers("Authorization: token  ${BuildConfig.GHP_TOKEN}")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<List<GithubUser>>

    @GET("users/{login}/following")
    @Headers("Authorization: token  ${BuildConfig.GHP_TOKEN}")
    fun getUserFollowings(
        @Path("login") login: String
    ): Call<List<GithubUser>>
}