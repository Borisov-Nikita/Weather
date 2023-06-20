package nik.borisov.weather.domain.entities

data class ForecastCommonItem(

    val localTimeEpoch: Long,
    val timeZone: String,
    val location: LocationItem,
    val currentWeather: CurrentWeatherItem,
    val forecast: List<ForecastItem>
    )
