package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class LocationDto(

    @SerializedName("name")
    val name: String,
    @SerializedName("tz_id")
    val timeZone: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long
)
