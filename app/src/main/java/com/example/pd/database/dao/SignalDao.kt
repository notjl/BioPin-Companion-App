package com.example.pd.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pd.database.models.Signal

@Dao
interface SignalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(signal: Signal)

    @Update
    fun update(signal: Signal)

    @Delete
    fun delete(signal: Signal)

    @Query("SELECT * FROM Signal")
    fun getAll(): List<Signal>
}