package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(

    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("forecast")
    val forecast: ForecastCommonDto
)
