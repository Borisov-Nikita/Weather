package nik.borisov.weather.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nik.borisov.weather.data.database.models.ForecastDbModel

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForecast(forecast: ForecastDbModel)

    @Query("SELECT * FROM forecast ORDER BY id DESC LIMIT 1")
    suspend fun getLastForecast(): ForecastDbModel?

    @Query("SELECT location FROM forecast ORDER BY id DESC LIMIT 1")
    suspend fun getLastLocation(): String?
}