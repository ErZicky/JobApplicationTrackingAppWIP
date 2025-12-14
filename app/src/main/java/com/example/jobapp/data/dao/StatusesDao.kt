package com.example.jobapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.model.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(status: Status)

    @Insert
    suspend fun insertAll(statuses: List<Status>)

    @Query("SELECT * from statuses ORDER by id ASC")
    fun getAllStatuses(): Flow<List<Status>>
}
