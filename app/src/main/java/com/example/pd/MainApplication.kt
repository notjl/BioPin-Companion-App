package com.example.pd

import android.app.Application
import com.example.pd.database.AppDatabase

class MainApplication: Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}