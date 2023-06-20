package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(

    @SerializedName("date_epoch")
    val dateEpoch: Long,
    @SerializedName("day")
    val shortForecastDay: ShortForecastDayDto,
    @SerializedName("hour")
    val forecastHours: List<ForecastHourDto>,
)
