package nik.borisov.weather.presentation.models

data class ForecastDayUi(

    val date: String,
    val conditionText: String,
    val conditionIcon: String,
    val maxMinTemp: CharSequence,
    val maxWindSpeed: CharSequence,
    val avgHumidity: CharSequence,
    val chanceOfPrecipitation: CharSequence
)
