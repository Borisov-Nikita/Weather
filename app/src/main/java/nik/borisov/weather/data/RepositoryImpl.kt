package nik.borisov.weather.data

import nik.borisov.weather.data.database.dao.ForecastDao
import nik.borisov.weather.data.network.ApiService
import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.repositories.Repository
import nik.borisov.weather.presentation.viewmodels.states.ServiceState
import nik.borisov.weather.utils.DataResult
import nik.borisov.weather.utils.DateTimeHelper
import nik.borisov.weather.utils.NetworkResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val forecastDao: ForecastDao,
    private val mapper: Mapper
) : Repository, NetworkResponse(), DateTimeHelper {

    override suspend fun getForecast(
        location: String?,
        serviceState: ServiceState
    ): DataResult<ForecastCommonItem> {
        val updatedLocation = location ?: forecastDao.getLastLocation()
        return when (serviceState.isNetworkEnabled) {
            true -> {
                if (updatedLocation != null) {
                    downloadForecast(updatedLocation)
                } else {
                    //TODO
                    DataResult.LocationError(message = "")
                }
            }
            false -> {
                val forecastCommonItem = getForecast()
                if (forecastCommonItem != null) {
                    DataResult.Success(forecastCommonItem)
                } else {
                    //TODO
                    DataResult.DatabaseError(message = "")
                }
            }
        }
    }

    private suspend fun downloadForecast(location: String): DataResult<ForecastCommonItem> {
        val networkResult = safeNetworkCall {
            apiService.loadForecast(location)
        }
        return if (networkResult is DataResult.Success) {
            try {
                if (networkResult.data == null) throw NullPointerException("ForecastWeatherResponse is null")
                val forecastCommonItem = mapper.mapForecastWeatherResponseToEntity(
                    networkResult.data.location,
                    networkResult.data.forecast
                )
                saveForecast(forecastCommonItem)
                return DataResult.Success(
                    forecastCommonItem
                )
            } catch (e: Exception) {
                DataResult.NetworkError(message = e.message)
            }
        } else {
            DataResult.NetworkError(message = networkResult.message)
        }
    }

    private suspend fun getForecast(): ForecastCommonItem? {
        val forecast =
            mapper.mapForecastDbModelToEntity(forecastDao.getLastForecast(), getCurrentDayEpoch())
        return if (forecast == null || forecast.forecastDays.isEmpty()) {
            null
        } else {
            forecast
        }
    }

    private suspend fun saveForecast(forecast: ForecastCommonItem) {
        forecastDao.addForecast(mapper.mapForecastCommonItemToDbModel(forecast))
    }
}
