package com.dicoding.fundamentalfirstsubmission1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.fundamentalfirstsubmission1.models.GithubUser


@Database(
    entities = [GithubUser::class],
    version = 1,
    )
abstract class UserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "favoriteDatabase.db").build()
                }
            }
            return INSTANCE
        }
    }
}