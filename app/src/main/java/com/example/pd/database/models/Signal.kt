package com.example.pd.database.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Signal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull @ColumnInfo
    val signal: String
)
