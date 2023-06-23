package nik.borisov.weather.domain.entities

data class ForecastCommonItem(

    val dateTimeInfo: DateTimeInfoItem,
    val location: LocationItem,
    val forecastDays: List<ForecastDayItem>,
    val forecastHours: List<ForecastHourItem>
)
