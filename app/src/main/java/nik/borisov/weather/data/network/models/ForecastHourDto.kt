package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastHourDto(

    @SerializedName("time_epoch")
    val timeEpoch: Long,
    @SerializedName("condition")
    val condition: ConditionDto,
    @SerializedName("temp_c")
    val temp: Double,
    @SerializedName("feelslike_c")
    val tempFeelsLike: Double,
    @SerializedName("wind_kph")
    val windSpeed: Double,
    @SerializedName("wind_dir")
    val windDirection: String,
    @SerializedName("gust_kph")
    val windGust: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int
)
