package com.dicoding.fundamentalfirstsubmission1.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val githubUsers: List<GithubUser>

) : Parcelable

@Entity(tableName = "favorite_users")
@Parcelize
data class GithubUser(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("login")
    val login: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int

    ) : Parcelable
