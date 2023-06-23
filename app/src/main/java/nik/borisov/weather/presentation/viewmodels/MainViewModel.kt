package nik.borisov.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nik.borisov.weather.domain.usecases.GetForecastUseCase
import nik.borisov.weather.presentation.Mapper
import nik.borisov.weather.presentation.models.ForecastCommonUi
import nik.borisov.weather.presentation.viewmodels.states.*
import nik.borisov.weather.utils.DataResult
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val mapper: Mapper
) : ViewModel() {

    private val _viewModelState = MutableLiveData<ViewModelState>()
    val viewModelState: LiveData<ViewModelState>
        get() = _viewModelState

    private val _forecast = MutableLiveData<ForecastCommonUi>()
    val forecast: LiveData<ForecastCommonUi>
        get() = _forecast

    private var _serviceState: ServiceState? = null
    private val serviceState: ServiceState
        get() = _serviceState ?: throw NullPointerException("ServiceState is null")

    fun getForecast(location: String?) {
        viewModelScope.launch {
            val dataResult =
                getForecastUseCase.getForecast(location, serviceState)
            when (dataResult) {
                is DataResult.Success -> {
                    _forecast.value = mapper.mapForecastCommonItemToUi(dataResult.data!!)
                    _viewModelState.value = ShowForecastFragment
                }
                is DataResult.NetworkError -> {
                    _viewModelState.value = ShowErrorFragment(dataResult.message!!)
                }
                is DataResult.DatabaseError -> {
                    _viewModelState.value = ShowErrorFragment(dataResult.message!!)
                }
                is DataResult.LocationError -> {
                    _viewModelState.value = ShowLocationFragment
                }
                is DataResult.Loading -> {}
            }
        }
    }

    fun retryGetForecast() {
        _viewModelState.value = SetViewModelState
    }

    fun setServiceState(state: ServiceState) {
        _serviceState = state
        refreshData()
    }

    fun refreshServiceState(state: ServiceState) {
        if (_serviceState == null || serviceState != state) {
            _serviceState = state
            refreshData()
        }
    }

    fun setViewModelState(state: ViewModelState) {
        _viewModelState.value = state
    }

    private fun refreshData() {
        when (serviceState.isLocationEnabled) {
            true -> {
                _viewModelState.value = GetLocationFromService
            }
            false -> {
                getForecast(null)
            }
        }
    }
}