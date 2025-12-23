package com.example.jobapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jobapp.data.dao.ApplicationDao
import com.example.jobapp.data.dao.StatusesDao
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.model.Status
import com.example.jobapp.data.repository.OfflineStatusRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Application::class,Status::class] , version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao
    abstract fun statusDao(): StatusesDao

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

                    .addCallback(PrepopulateStatusesCallback(context))
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }



    }
}



private class PrepopulateStatusesCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    // Questo metodo viene chiamato solo quando il database viene creato per la prima volta
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val statusList = listOf(
            "Submitted",
            "Rejected",
            "Interviewing",
            "Rejected after interviews",
            "No answer"
        )

        // Usiamo una transazione per efficienza e sicurezza
        db.beginTransaction()
        try {
            for (statusName in statusList) {
                val values = android.content.ContentValues().apply {
                    put("status", statusName)
                }
                db.insert("statuses", android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

}
