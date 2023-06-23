package nik.borisov.weather.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nik.borisov.weather.domain.entities.ForecastCommonItem

class Converters {

    @TypeConverter
    fun toEntity(json: String): ForecastCommonItem {
        val type = object : TypeToken<ForecastCommonItem>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(item: ForecastCommonItem): String {
        val type = object : TypeToken<ForecastCommonItem>() {}.type
        return Gson().toJson(item, type)
    }
}