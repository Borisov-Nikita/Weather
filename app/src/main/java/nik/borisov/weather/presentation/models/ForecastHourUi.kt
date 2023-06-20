package nik.borisov.weather.presentation.models

data class ForecastHourUi(

    val time: String,
    val conditionText: String,
    val conditionIcon: String,
    val temp: String,
    val tempFeelsLike: String,
    val windSpeed: String,
    val windDirection: String,
    val windGust: String,
    val humidity: String,
    val chanceOfRain: String,
    val chanceOfSnow: String
)
