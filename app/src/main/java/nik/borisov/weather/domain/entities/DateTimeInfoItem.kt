package nik.borisov.weather.domain.entities

data class DateTimeInfoItem(

    val lastUpdateTimeEpoch: Long,
    val startForecastTimeEpoch: Long,
    val finishForecastTimeEpoch: Long,
    val timeZone: String,
)
