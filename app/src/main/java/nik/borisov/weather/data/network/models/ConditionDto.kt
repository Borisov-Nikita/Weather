package nik.borisov.weather.data.network.models

import com.google.gson.annotations.SerializedName

data class ConditionDto(

    @SerializedName("text")
    val text: String,
    @SerializedName("icon")
    val icon: String,
)
