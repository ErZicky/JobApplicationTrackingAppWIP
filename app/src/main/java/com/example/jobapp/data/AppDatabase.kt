package com.example.jobapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobapp.data.dao.ApplicationDao
import com.example.jobapp.data.model.Application

@Database(entities = [Application::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "job_application_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
