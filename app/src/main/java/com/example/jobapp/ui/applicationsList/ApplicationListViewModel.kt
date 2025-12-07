package com.example.jobapp.ui.applicationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jobapp.JobApplicationAppHolder
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.repository.OfflineApplicationsRepository
import com.example.jobapp.ui.createapplication.ApplicationCreationViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ApplicationListViewModel(repository: OfflineApplicationsRepository) : ViewModel() {

    val applicationsList: StateFlow<List<Application>> =
        repository.getAllApplicationsStream().stateIn(
            scope = viewModelScope,
            //Il flusso viene attivato solo quando c'è almeno un osservatore.
            //Quando l’ultimo osservatore si stacca, il flusso rimane attivo ancora per 5 secondi prima di fermarsi:
            //impedisce stop/start troppo frequenti se le UI si ricollegano rapidamente.
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobApplicationAppHolder)
                val repository = application.container.applicationsRepository
                ApplicationListViewModel(repository = repository)
            }
        }
    }
}
