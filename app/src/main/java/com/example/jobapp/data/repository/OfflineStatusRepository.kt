package com.example.jobapp.data.repository

import com.example.jobapp.data.dao.ApplicationDao
import com.example.jobapp.data.dao.StatusesDao
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.model.Status
import kotlinx.coroutines.flow.Flow

class OfflineStatusRepository(private val statusDao: StatusesDao) {

    fun getAllStatusStream(): Flow<List<Status>> = statusDao.getAllStatuses()

    suspend fun insertStatus(nStatus: Status) = statusDao.insert(nStatus)


}
