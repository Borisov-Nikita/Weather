package nik.borisov.weather.presentation.models

data class ForecastCommonUi(

    val location: String,
    val currentWeather: CurrentWeatherUi,
    val forecastDays: List<ForecastDayUi>,
    val forecastHours: List<ForecastHourUi>
)
