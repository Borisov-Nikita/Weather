package nik.borisov.weather.domain.entities

data class ForecastItem(

    val forecastDay: ForecastDayItem,
    val forecastHours: List<ForecastHourItem>,
)
