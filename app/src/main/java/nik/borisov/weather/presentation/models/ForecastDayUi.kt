package nik.borisov.weather.presentation.models

data class ForecastDayUi(

    val date: String,
    val conditionText: String,
    val conditionIcon: String,
    val maxMinTemp: String,
    val maxWindSpeed: String,
    val avgHumidity: String,
    val chanceOfRain: String,
    val chanceOfSnow: String
)
