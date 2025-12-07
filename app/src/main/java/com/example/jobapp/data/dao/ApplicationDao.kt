package com.example.jobapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jobapp.data.model.Application
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(application: Application)

    @Update
    suspend fun update(application: Application)

    @Delete
    suspend fun delete(application: Application)

    @Query("SELECT * from applications ORDER BY date DESC")
    fun getAllApplications(): Flow<List<Application>>
}
