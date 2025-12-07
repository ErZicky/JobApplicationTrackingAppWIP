package com.example.jobapp

import android.app.Application
import com.example.jobapp.data.AppContainer
import com.example.jobapp.data.DependencyContainer

class JobApplicationAppHolder : Application() {

    /**
     * this class basically holds the instance of the app container.
     * AppContainer instance used by the rest of classes to obtain dependencies
     * this class is created when the application starts
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DependencyContainer(this)
    }
}
