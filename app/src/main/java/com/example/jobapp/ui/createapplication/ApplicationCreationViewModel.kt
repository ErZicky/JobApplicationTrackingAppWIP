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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class ApplicationCreationViewModel(private val repository: OfflineApplicationsRepository) : ViewModel() {

    suspend fun saveApplication(company: String, role: String, status: String, link: String,  companyLink: String?,  date : Long) {
        val newApplication = Application(
            company = company,
            role = role,
            status = status,
            date = date,
            link = link,
            companyWebsite = companyLink
        )
        repository.insertApplication(newApplication)
    }

     fun onSaveClicked(company: String, role: String, status: String, link: String,  companyLink: String?, date : Long)
    {
        viewModelScope.launch {

          var companyLinkManipulated : String? = companyLink


          if(companyLink == "")
               companyLinkManipulated = null

            saveApplication(company, role, status, link, companyLinkManipulated, date)
        }

    }
    //like static in java
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JobApplicationAppHolder)
                val repository = application.container.applicationsRepository
                ApplicationCreationViewModel(repository = repository)
            }
        }
    }
}