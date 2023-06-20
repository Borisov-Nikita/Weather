package nik.borisov.weather.data

import nik.borisov.weather.data.network.models.*
import nik.borisov.weather.domain.entities.*
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapForecastWeatherResponseToEntity(
        location: LocationDto,
        currentWeather: CurrentWeatherDto,
        forecast: ForecastCommonDto
    ): ForecastCommonItem {
        return ForecastCommonItem(
            localTimeEpoch = location.localtimeEpoch,
            timeZone = location.timeZone,
            location = mapLocationDtoToEntity(location),
            currentWeather = mapCurrentWeatherDtoToEntity(currentWeather),
            forecast = mapForecastCommonDtoToEntity(forecast)
        )
    }

    private fun mapLocationDtoToEntity(dto: LocationDto): LocationItem {
        return LocationItem(
            name = dto.name,
            region = dto.region,
            country = dto.country
        )
    }

    private fun mapCurrentWeatherDtoToEntity(dto: CurrentWeatherDto): CurrentWeatherItem {
        return CurrentWeatherItem(
            lastUpdateEpoch = dto.lastUpdateEpoch,
            conditionText = dto.condition.text,
            conditionIcon = dto.condition.icon,
            temp = dto.temp,
            tempFeelsLike = dto.tempFeelsLike,
            windSpeed = dto.windSpeed,
            windDirection = dto.windDirection,
            windGust = dto.windGust,
            humidity = dto.humidity
        )
    }

    private fun mapForecastCommonDtoToEntity(dto: ForecastCommonDto): List<ForecastItem> {
        return mutableListOf<ForecastItem>().apply {
            for (item in dto.forecastDay) {
                add(mapForecastDayDtoToEntity(item))
            }
        }.toList()
    }

    private fun mapForecastDayDtoToEntity(dto: ForecastDayDto): ForecastItem {
        return ForecastItem(
            forecastDay = mapShortForecastDayDtoToEntity(dto.shortForecastDay, dto.dateEpoch),
            forecastHours = dto.forecastHours.map {
                mapForecastHoursDtoToEntity(it)
            }
        )
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