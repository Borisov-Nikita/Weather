package nik.borisov.weather.presentation.models

data class ForecastHourUi(

    val time: String,
    val conditionText: String,
    val conditionIcon: String,
    val temp: CharSequence,
    val tempFeelsLike: String,
    val windSpeed: CharSequence,
    val windDirection: String,
    val windGust: String,
    val humidity: CharSequence,
    val chanceOfPrecipitation: CharSequence
)
