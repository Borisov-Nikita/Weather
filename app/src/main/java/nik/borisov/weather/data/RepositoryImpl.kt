package nik.borisov.weather.data

import nik.borisov.weather.data.network.ApiService
import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.repositories.Repository
import nik.borisov.weather.utils.DataResult
import nik.borisov.weather.utils.NetworkResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper
) : Repository, NetworkResponse() {

    override suspend fun downloadForecast(location: String): DataResult<ForecastCommonItem> {
        val networkResult = safeNetworkCall {
            apiService.loadForecast(location)
        }
        return if (networkResult is DataResult.Success) {
            try {
                if (networkResult.data == null) throw NullPointerException("ForecastWeatherResponse is null")
                return DataResult.Success(
                    mapper.mapForecastWeatherResponseToEntity(
                        networkResult.data.location,
                        networkResult.data.currentWeather,
                        networkResult.data.forecast
                    )
                )
            } catch (e: Exception) {
                DataResult.Error(message = e.message)
            }
        } else {
            DataResult.Error(message = networkResult.message)
        }
    }
}