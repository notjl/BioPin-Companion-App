package com.example.pd.database.models

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val username: String,

    @NonNull @ColumnInfo
    val firstName: String,

    @NonNull @ColumnInfo
    val surname: String,

    @NonNull @ColumnInfo
    val password: String
)
