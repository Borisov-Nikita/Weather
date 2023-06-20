package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ShortForecastDayDto(

    @SerializedName("maxtemp_c")
    val maxTemp: Double,
    @SerializedName("mintemp_c")
    val minTemp: Double,
    @SerializedName("maxwind_kph")
    val maxWindSpeed: Double,
    @SerializedName("avghumidity")
    val avgHumidity: Int,
    @SerializedName("daily_chance_of_rain")
    val chanceOfRain: Int,
    @SerializedName("daily_chance_of_snow")
    val chanceOfSnow: Int,
    @SerializedName("condition")
    val condition: ConditionDto
)
