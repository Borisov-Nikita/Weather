package nik.borisov.weather.domain.repositories

import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.utils.DataResult

interface Repository {

    suspend fun downloadForecast(location: String): DataResult<ForecastCommonItem>
}