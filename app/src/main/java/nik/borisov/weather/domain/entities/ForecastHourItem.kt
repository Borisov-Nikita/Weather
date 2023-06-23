package nik.borisov.weather.domain.entities

data class ForecastHourItem(

    val timeEpoch: Long,
    val conditionText: String,
    val conditionIcon: String,
    val temp: Double,
    val tempFeelsLike: Double,
    val windSpeed: Double,
    val windDirection: String,
    val windGust: Double,
    val humidity: Int,
    val chanceOfRain: Int,
    val chanceOfSnow: Int
)
