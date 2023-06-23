package nik.borisov.weather.domain.entities

data class ForecastDayItem(

    val dateEpoch: Long,
    val conditionText: String,
    val conditionIcon: String,
    val maxTemp: Double,
    val minTemp: Double,
    val maxWindSpeed: Double,
    val avgHumidity: Int,
    val chanceOfRain: Int,
    val chanceOfSnow: Int
)
