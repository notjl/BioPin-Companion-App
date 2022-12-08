package com.example.pd.database.dao

import androidx.room.*
import com.example.pd.database.models.Signal
import kotlinx.coroutines.flow.Flow

@Dao
interface SignalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(signal: Signal)

    @Update
    fun update(signal: Signal)

    @Delete
    fun delete(signal: Signal)

    @Query("SELECT * FROM Signal")
    fun getAll(): Flow<List<Signal>>

    @Query("SELECT * FROM Signal WHERE id = :id")
    fun getSignal(id: Int): Flow<Signal>
}