package com.example.jobapp.ui.createapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jobapp.JobApplicationAppHolder
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.model.Status
import com.example.jobapp.data.repository.OfflineApplicationsRepository
import com.example.jobapp.data.repository.OfflineStatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class StatusViewModel(private val repository: OfflineStatusRepository) : ViewModel() {


    val statusList: StateFlow<List<Status>> =
        repository.getAllStatusStream().stateIn(
            scope = viewModelScope,
            //Il flusso viene attivato solo quando c'è almeno un osservatore.
            //Quando l’ultimo osservatore si stacca, il flusso rimane attivo ancora per 5 secondi prima di fermarsi:
            //impedisce stop/start troppo frequenti se le UI si ricollegano rapidamente.
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = mutableListOf<Status>()
        )

    suspend fun saveNewStatus(status: String)
    {
        val newStatus = Status(
            status = status
        )
        repository.insertStatus(newStatus)
    }

    fun onDialogSubmit(status : String)
    {
        viewModelScope.launch {
            saveNewStatus(status)
        }

    }

    //like static in java
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobApplicationAppHolder)
                val repository = application.container.statusRepository
                StatusViewModel(repository = repository)
            }
        }
    }
}