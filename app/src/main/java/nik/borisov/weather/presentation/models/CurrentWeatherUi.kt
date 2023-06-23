package nik.borisov.weather.presentation.models

data class CurrentWeatherUi(

    val lastUpdate: String,
    val conditionText: String,
    val conditionIcon: String,
    val temp: CharSequence,
    val tempFeelsLike: CharSequence,
    val maxMinTemp: CharSequence,
    val windSpeed: CharSequence,
    val windDirection: String,
    val windGust: CharSequence,
    val humidity: CharSequence,
    val chanceOfPrecipitation: CharSequence
)
