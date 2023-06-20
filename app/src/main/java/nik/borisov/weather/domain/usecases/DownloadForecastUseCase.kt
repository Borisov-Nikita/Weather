package nik.borisov.weather.domain.usecases

import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.repositories.Repository
import nik.borisov.weather.utils.DataResult
import javax.inject.Inject

class DownloadForecastUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun downloadForecast(location: String): DataResult<ForecastCommonItem> {
        return repository.downloadForecast(location)
    }
}