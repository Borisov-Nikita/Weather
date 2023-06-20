package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastCommonDto(

    @SerializedName("forecastday")
    val forecastDay: List<ForecastDayDto>
)
