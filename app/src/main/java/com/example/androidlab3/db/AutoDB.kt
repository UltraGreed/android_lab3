package com.example.androidlab3.db

import android.content.Context
import androidx.room.*
import com.example.androidlab3.domain.AutoEntity

@Database(entities = [AutoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAutoDao(): AutoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database.db"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
