package com.example.jobapp.data

import android.content.Context
import com.example.jobapp.data.dao.ApplicationDao
import com.example.jobapp.data.repository.OfflineApplicationsRepository

//a simple helper to get the DB instance around the app more cleanly, to avoid to write everytime:
// val repo = OfflineApplicationsRepository(AppDatabase.getDatabase(myContext).applicationDao())

interface AppContainer {
    val applicationsRepository: OfflineApplicationsRepository
}

class DependencyContainer (private val context: Context) : AppContainer{

    override val applicationsRepository: OfflineApplicationsRepository by lazy {

        //Ottiene o crea instanza Database. -> Dal database estrae il DAO. -> Passa il DAO al Repository. che fa le associazioni
        val dbDao = AppDatabase.getDatabase(context).applicationDao();
        OfflineApplicationsRepository(dbDao)
    }
}