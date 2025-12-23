package com.example.jobapp.ui.createapplication

import android.content.Context
import android.location.Geocoder
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.util.Date
import java.util.Locale

class ApplicationCreationViewModel(private val repository: OfflineApplicationsRepository) : ViewModel() {



    //map status
    private val _mapCenter = MutableStateFlow(GeoPoint(41.9028, 12.4964)) // Default Roma
    val mapCenter = _mapCenter.asStateFlow()

    private val _markerPoint = MutableStateFlow<GeoPoint?>(null)
    private val _markerPointTitle = MutableStateFlow("")
    private val _mapZoom = MutableStateFlow(5.0)
    val markerPoint = _markerPoint.asStateFlow()
    val markerPointTitle  = _markerPointTitle
    val mapZoom = _mapZoom
    suspend fun saveApplication(company: String, role: String, status: String, link: String,  companyLink: String?,  date : Long) {
        val newApplication = Application(
            company = company,
            role = role,
            status = status,
            date = date,
            link = link,
            companyWebsite = companyLink ,
            companyLocation = null,
            companyLatitude = null,
            companyLongitude = null
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

    fun searchLocation(context: Context, address: String) {
        if (address.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                // Eseguiamo la ricerca
                val results = geocoder.getFromLocationName(address, 1)

                if (!results.isNullOrEmpty()) {
                    val location = results[0]
                    val newPoint = GeoPoint(location.latitude, location.longitude)

                    // Aggiorniamo gli stati (StateFlow è thread-safe)
                    _mapCenter.value = newPoint
                    _markerPoint.value = newPoint



                    var locationOverlay : StringBuilder = StringBuilder()

                    if(!location.locality.isNullOrEmpty())
                        locationOverlay.append( "${location.locality}, ")


                    locationOverlay.append(location.countryName)

                    _markerPointTitle.value = locationOverlay.toString()
                    _mapZoom.value = 6.5
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //todo display something on the map
            }
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