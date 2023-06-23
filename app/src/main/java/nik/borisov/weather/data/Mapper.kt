package nik.borisov.weather.data

import nik.borisov.weather.data.database.models.ForecastDbModel
import nik.borisov.weather.data.network.models.ForecastCommonDto
import nik.borisov.weather.data.network.models.ForecastHourDto
import nik.borisov.weather.data.network.models.LocationDto
import nik.borisov.weather.data.network.models.ShortForecastDayDto
import nik.borisov.weather.domain.entities.*
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapForecastWeatherResponseToEntity(
        location: LocationDto,
        forecast: ForecastCommonDto
    ): ForecastCommonItem {
        val forecastHourList = getForecastHourList(forecast)
        return ForecastCommonItem(
            dateTimeInfo = getDateTimeInfo(forecastHourList, location),
            location = mapLocationDtoToEntity(location),
            forecastDays = getForecastDayList(forecast),
            forecastHours = forecastHourList
        )
    }

    fun mapForecastCommonItemToDbModel(item: ForecastCommonItem): ForecastDbModel {
        return ForecastDbModel(location = item.location.name, forecast = item)
    }

    fun mapForecastDbModelToEntity(dbModel: ForecastDbModel?, date: Long): ForecastCommonItem? {
        return dbModel?.forecast?.copy(
            forecastDays = dbModel.forecast.forecastDays.filter { it.dateEpoch >= date },
            forecastHours = dbModel.forecast.forecastHours.filter { it.timeEpoch >= date }
        )
    }

    private fun getDateTimeInfo(
        forecastHourList: List<ForecastHourItem>,
        location: LocationDto
    ): DateTimeInfoItem {
        return DateTimeInfoItem(
            lastUpdateTimeEpoch = location.localtimeEpoch,
            startForecastTimeEpoch = forecastHourList.first().timeEpoch,
            finishForecastTimeEpoch = forecastHourList.last().timeEpoch,
            timeZone = location.timeZone
        )
    }

    private fun mapLocationDtoToEntity(dto: LocationDto): LocationItem {
        return LocationItem(
            name = dto.name,
            region = dto.region,
            country = dto.country
        )
    }

    private fun getForecastDayList(forecast: ForecastCommonDto): List<ForecastDayItem> {
        return mutableListOf<ForecastDayItem>().apply {
            for (item in forecast.forecastDay) {
                add(mapShortForecastDayDtoToEntity(item.shortForecastDay, item.dateEpoch))
            }
        }.toList()
    }

    private fun getForecastHourList(forecast: ForecastCommonDto): List<ForecastHourItem> {
        return mutableListOf<ForecastHourItem>().apply {
            for (item in forecast.forecastDay) {
                addAll(item.forecastHours.map { mapForecastHoursDtoToEntity(it) })
            }
        }.toList()
    }

    private fun mapShortForecastDayDtoToEntity(
        dto: ShortForecastDayDto,
        date: Long
    ): ForecastDayItem {
        return ForecastDayItem(
            dateEpoch = date,
            conditionText = dto.condition.text,
            conditionIcon = dto.condition.icon,
            maxTemp = dto.maxTemp,
            minTemp = dto.minTemp,
            maxWindSpeed = dto.maxWindSpeed,
            avgHumidity = dto.avgHumidity,
            chanceOfRain = dto.chanceOfRain,
            chanceOfSnow = dto.chanceOfSnow,
        )
    }

    private fun mapForecastHoursDtoToEntity(dto: ForecastHourDto): ForecastHourItem {
        return ForecastHourItem(
            timeEpoch = dto.timeEpoch,
            conditionText = dto.condition.text,
            conditionIcon = dto.condition.icon,
            temp = dto.temp,
            tempFeelsLike = dto.tempFeelsLike,
            windSpeed = dto.windSpeed,
            windDirection = dto.windDirection,
            windGust = dto.windGust,
            humidity = dto.humidity,
            chanceOfRain = dto.chanceOfRain,
            chanceOfSnow = dto.chanceOfSnow
        )
    }
}