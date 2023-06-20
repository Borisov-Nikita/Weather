package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(

    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("current")
    val currentWeather: CurrentWeatherDto,
    @SerializedName("forecast")
    val forecast: ForecastCommonDto
)
