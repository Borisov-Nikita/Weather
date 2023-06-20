package nik.borisov.weather.domain.entities

data class CurrentWeatherItem(

    val lastUpdateEpoch: Long,
    val conditionText: String,
    val conditionIcon: String,
    val temp: Double,
    val tempFeelsLike: Double,
    val windSpeed: Double,
    val windDirection: String,
    val windGust: Double,
    val humidity: Int
)
