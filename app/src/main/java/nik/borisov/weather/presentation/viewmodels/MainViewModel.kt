package nik.borisov.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nik.borisov.weather.domain.usecases.DownloadForecastUseCase
import nik.borisov.weather.presentation.Mapper
import nik.borisov.weather.presentation.models.ForecastCommonUi
import nik.borisov.weather.presentation.viewmodels.states.*
import nik.borisov.weather.utils.DataResult
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val downloadForecastUseCase: DownloadForecastUseCase,
    private val mapper: Mapper
) : ViewModel() {

    private val _fragmentState = MutableLiveData<FragmentState>()
    val fragmentState: LiveData<FragmentState>
        get() = _fragmentState

    private val _forecast = MutableLiveData<ForecastCommonUi>()
    val forecast: LiveData<ForecastCommonUi>
        get() = _forecast

    private var serviceState: ServiceState? = null
    private var currentLocation: String? = null

    fun downloadForecast(location: String) {
        currentLocation = location
        viewModelScope.launch {
            val dataResult = downloadForecastUseCase.downloadForecast(location)
            if (dataResult is DataResult.Success) {
                _forecast.value = mapper.mapForecastCommonItemToUi(dataResult.data!!)
                _fragmentState.value = ShowForecastFragment
            } else {
                _fragmentState.value = ShowErrorFragment(dataResult.message!!)
            }
        }
    }

    fun setServiceState(state: ServiceState) {
        if (serviceState == null || serviceState != state) {
            serviceState = state
            updateStates()
        }
    }

    private fun updateStates() {
        when (serviceState?.isNetworkEnabled) {
            true -> {
                when (serviceState?.isLocationEnabled) {
                    true -> {
                        _fragmentState.value = GetLocationFromService
                    }
                    false -> {
                        //TODO get last location from DB
                    }
                    else -> {
                        //TODO
                        _fragmentState.value = ShowErrorFragment("")
                    }
                }
            }
            false -> {
                //TODO get last forecast from DB
            }
            else -> {
                //TODO
                _fragmentState.value = ShowErrorFragment("")
            }
        }
    }
}