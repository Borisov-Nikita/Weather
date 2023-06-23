package nik.borisov.weather.domain.usecases

import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.repositories.Repository
import nik.borisov.weather.presentation.viewmodels.states.ServiceState
import nik.borisov.weather.utils.DataResult
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun getForecast(
        location: String?,
        serviceState: ServiceState
    ): DataResult<ForecastCommonItem> {
        return repository.getForecast(location, serviceState)
    }
}