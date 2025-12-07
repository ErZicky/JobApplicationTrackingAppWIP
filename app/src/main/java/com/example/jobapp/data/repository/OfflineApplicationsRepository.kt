package com.example.jobapp.data.repository

import com.example.jobapp.data.dao.ApplicationDao
import com.example.jobapp.data.model.Application
import kotlinx.coroutines.flow.Flow

class OfflineApplicationsRepository(private val applicationDao: ApplicationDao) {

    fun getAllApplicationsStream(): Flow<List<Application>> = applicationDao.getAllApplications()

    suspend fun insertApplication(application: Application) = applicationDao.insert(application)

    suspend fun deleteApplication(application: Application) = applicationDao.delete(application)


    //è come scrivere una funzione che fa return applicationDao.update(application)
    suspend fun updateApplication(application: Application) = applicationDao.update(application)
}
