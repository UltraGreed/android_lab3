package com.example.androidlab3

import android.app.Application
import com.example.androidlab3.db.AppDatabase
import com.example.androidlab3.db.AutoLocalSource
import com.example.androidlab3.repository.AutoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val dataBase by lazy {
        AppDatabase.getDatabase(this)
    }

    private val autoLocalSource by lazy {
        AutoLocalSource(dataBase.getAutoDao())
    }

    val autoRepository by lazy {
        AutoRepository(autoLocalSource)
    }
}
