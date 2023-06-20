package nik.borisov.weather.presentation

import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.entities.ForecastDayItem
import nik.borisov.weather.domain.entities.ForecastHourItem
import nik.borisov.weather.domain.entities.ForecastItem
import nik.borisov.weather.presentation.models.CurrentWeatherUi
import nik.borisov.weather.presentation.models.ForecastCommonUi
import nik.borisov.weather.presentation.models.ForecastDayUi
import nik.borisov.weather.presentation.models.ForecastHourUi
import nik.borisov.weather.utils.TimeConverter
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

class Mapper @Inject constructor() : TimeConverter {

    fun mapForecastCommonItemToUi(item: ForecastCommonItem): ForecastCommonUi {
        return ForecastCommonUi(
            location = item.location.name,
            currentWeather = getCurrentHourForecast(item.forecast, item.timeZone),
            forecastDays = getForecastDayList(item.forecast, item.timeZone),
            forecastHours = getTwentyFourHoursForecasts(item.forecast, item.timeZone)
        )
    }

//    private fun mapCurrentWeatherItemToUi(
//        item: CurrentWeatherItem,
//        timeZoneId: String
//    ): CurrentWeatherUi {
//        return CurrentWeatherUi(
//            lastUpdate = convertTimeDateFromEpochToString(
//                item.lastUpdateEpoch,
//                timeZoneId,
//                "dd MMM HH:mm"
//            ),
//            conditionText = item.conditionText,
//            conditionIcon = "https:${item.conditionIcon}",
//            temp = buildString {
//                append(
//                    item.temp.roundToInt().toString(),
//                    "ºC"
//                )
//            },
//            tempFeelsLike = item.tempFeelsLike.roundToInt().toString(),
//            windSpeed = item.windSpeed.roundToInt().toString(),
//            windDirection = item.windDirection,
//            windGust = item.windGust.roundToInt().toString(),
//            humidity = item.humidity.toString()
//        )
//    }

    private fun mapCurrentWeatherUi(
        forecastDayItem: ForecastDayItem,
        forecastHourItem: ForecastHourItem,
        timeZoneId: String
    ): CurrentWeatherUi {
        return CurrentWeatherUi(
            lastUpdate = convertTimeDateFromEpochToString(
                forecastDayItem.dateEpoch,
                timeZoneId,
                "dd MMM HH:mm"
            ),
            conditionText = forecastHourItem.conditionText,
            conditionIcon = "https:${forecastHourItem.conditionIcon}",
            temp = buildString {
                append(
                    forecastHourItem.temp.roundToInt().toString(),
                    "ºC"
                )
            },
            tempFeelsLike = forecastHourItem.tempFeelsLike.roundToInt().toString(),
            windSpeed = forecastHourItem.windSpeed.roundToInt().toString(),
            windDirection = forecastHourItem.windDirection,
            windGust = forecastHourItem.windGust.roundToInt().toString(),
            humidity = forecastHourItem.humidity.toString()
        )
    }

    private fun mapForecastDayItemToUi(item: ForecastDayItem, timeZoneId: String): ForecastDayUi {
        return ForecastDayUi(
            date = convertTimeDateFromEpochToString(item.dateEpoch, timeZoneId, "dd MMM"),
            conditionText = item.conditionText,
            conditionIcon = "https:${item.conditionIcon}",
            maxMinTemp = buildString {
                append(
                    item.maxTemp.roundToInt().toString(),
                    "/",
                    item.minTemp.roundToInt().toString(),
                    "ºC"
                )
            },
            maxWindSpeed = item.maxWindSpeed.roundToInt().toString(),
            avgHumidity = item.avgHumidity.toString(),
            chanceOfRain = item.chanceOfRain.toString(),
            chanceOfSnow = item.chanceOfSnow.toString()
        )
    }

    private fun mapForecastHourItemToUi(
        item: ForecastHourItem,
        timeZoneId: String
    ): ForecastHourUi {
        return ForecastHourUi(
            time = convertTimeDateFromEpochToString(item.timeEpoch, timeZoneId, "HH:mm"),
            conditionText = item.conditionText,
            conditionIcon = "https:${item.conditionIcon}",
            temp = buildString {
                append(
                    item.temp.roundToInt().toString(),
                    "ºC"
                )
            },
            tempFeelsLike = item.tempFeelsLike.roundToInt().toString(),
            windSpeed = item.windSpeed.roundToInt().toString(),
            windDirection = item.windDirection,
            windGust = item.windGust.roundToInt().toString(),
            humidity = item.humidity.toString(),
            chanceOfRain = item.chanceOfRain.toString(),
            chanceOfSnow = item.chanceOfSnow.toString(),
        )
    }

    private fun getCurrentHourForecast(
        items: List<ForecastItem>,
        timeZoneId: String
    ): CurrentWeatherUi {
        val desiredForecastItem =
            items.filter { it.forecastDay.dateEpoch == getCurrentDayEpoch() }.let { it[0] }
        return mapCurrentWeatherUi(
            desiredForecastItem.forecastDay,
            desiredForecastItem.forecastHours.filter { it.timeEpoch == getCurrentHourEpoch() }
                .let { it[0] },
            timeZoneId
        )
    }

    private fun getForecastDayList(
        forecastItemList: List<ForecastItem>,
        timeZoneId: String
    ): List<ForecastDayUi> {
        return mutableListOf<ForecastDayUi>().apply {
            for (item in forecastItemList) {
                add(mapForecastDayItemToUi(item.forecastDay, timeZoneId))
            }
        }.toList()
    }

    private fun getTwentyFourHoursForecasts(
        forecastItemList: List<ForecastItem>,
        timeZoneId: String
    ): List<ForecastHourUi> {
        val timeNow = ZonedDateTime.now().toEpochSecond()
        val timePlusTwentyFourHours = ZonedDateTime.now().plusHours(24).toEpochSecond()
        return mutableListOf<ForecastHourUi>().apply {
            for (item in forecastItemList) {
                addAll(item.forecastHours.filter {
                    it.timeEpoch in timeNow..timePlusTwentyFourHours
                }.map {
                    mapForecastHourItemToUi(it, timeZoneId)
                })
            }
        }.toList()
    }
}