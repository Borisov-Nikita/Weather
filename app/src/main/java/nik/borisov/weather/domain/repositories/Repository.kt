package nik.borisov.weather.domain.repositories

import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.presentation.viewmodels.states.ServiceState
import nik.borisov.weather.utils.DataResult

interface Repository {

    suspend fun getForecast(
        location: String?,
        serviceState: ServiceState
    ): DataResult<ForecastCommonItem>
}