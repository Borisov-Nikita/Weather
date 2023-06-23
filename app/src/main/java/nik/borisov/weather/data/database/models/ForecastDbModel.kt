package nik.borisov.weather.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import nik.borisov.weather.data.database.Converters
import nik.borisov.weather.domain.entities.ForecastCommonItem

@TypeConverters(Converters::class)
@Entity(tableName = "forecast")
data class ForecastDbModel(

    @PrimaryKey
    val id: Int = 1,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "forecast")
    val forecast: ForecastCommonItem
)
