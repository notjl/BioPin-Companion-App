package com.example.pd.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.midterms.database.models.User
import com.example.pd.database.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    fun get(username: String, password: String): User?

}