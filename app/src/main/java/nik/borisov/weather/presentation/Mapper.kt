package nik.borisov.weather.presentation

import android.text.SpannableString
import android.text.TextUtils
import nik.borisov.weather.domain.entities.DateTimeInfoItem
import nik.borisov.weather.domain.entities.ForecastCommonItem
import nik.borisov.weather.domain.entities.ForecastDayItem
import nik.borisov.weather.domain.entities.ForecastHourItem
import nik.borisov.weather.presentation.models.CurrentWeatherUi
import nik.borisov.weather.presentation.models.ForecastCommonUi
import nik.borisov.weather.presentation.models.ForecastDayUi
import nik.borisov.weather.presentation.models.ForecastHourUi
import nik.borisov.weather.utils.DateTimeHelper
import nik.borisov.weather.utils.TopAlignSuperscriptSpan
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

class Mapper @Inject constructor() : DateTimeHelper {

    fun mapForecastCommonItemToUi(item: ForecastCommonItem): ForecastCommonUi {
        return ForecastCommonUi(
            location = item.location.name,
            currentWeather = getCurrentHourForecast(item),
            forecastDays = item.forecastDays.map {
                mapForecastDayItemToUi(
                    it,
                    item.dateTimeInfo.timeZone
                )
            },
            forecastHours = getTwentyFourHoursForecasts(
                item.forecastHours,
                item.dateTimeInfo.timeZone
            )
        )
    }

    private fun getCurrentHourForecast(
        item: ForecastCommonItem
    ): CurrentWeatherUi {
        return mapCurrentWeatherUi(
            item.forecastDays.first { it.dateEpoch == getCurrentDayEpoch() },
            item.forecastHours.first { it.timeEpoch == getCurrentHourEpoch() },
            item.dateTimeInfo
        )
    }

    private fun mapCurrentWeatherUi(
        forecastDayItem: ForecastDayItem,
        forecastHourItem: ForecastHourItem,
        dateTimeInfoItem: DateTimeInfoItem
    ): CurrentWeatherUi {
        return CurrentWeatherUi(
            lastUpdate = buildString {
                append(
                    "Last update ",
                    convertTimeDateFromEpochToString(
                        dateTimeInfoItem.lastUpdateTimeEpoch,
                        dateTimeInfoItem.timeZone,
                        "dd MMM HH:mm"
                    )
                )
            },
            conditionText = forecastHourItem.conditionText,
            conditionIcon = "https:${forecastHourItem.conditionIcon}",
            temp = getSpannableValueMeasureString(
                forecastHourItem.temp.roundToInt().toString(),
                "ºC"
            ),
            tempFeelsLike = buildString {
                append(
                    "Feels like ",
                    getSpannableValueMeasureString(
                        forecastHourItem.tempFeelsLike.roundToInt().toString(),
                        "ºC"
                    )
                )
            },
            maxMinTemp = getSpannableValueMeasureString(
                buildString {
                    append(
                        forecastDayItem.maxTemp.roundToInt().toString(),
                        "/",
                        forecastDayItem.minTemp.roundToInt().toString()
                    )
                },
                "ºC"
            ),
            windSpeed = getSpannableValueMeasureString(
                forecastHourItem.windSpeed.roundToInt().toString(),
                "kmph"
            ),
            windDirection = forecastHourItem.windDirection,
            windGust = getSpannableValueMeasureString(
                forecastHourItem.windGust.roundToInt().toString(),
                "kmph"
            ),
            humidity = getSpannableValueMeasureString(
                forecastHourItem.humidity.toString(),
                "%"
            ),
            chanceOfPrecipitation = getSpannableValueMeasureString(
                maxOf(
                    forecastHourItem.chanceOfRain,
                    forecastHourItem.chanceOfSnow
                ).toString(),
                "%"
            ),
        )
    }

    private fun mapForecastDayItemToUi(item: ForecastDayItem, timeZoneId: String): ForecastDayUi {
        return ForecastDayUi(
            date = convertTimeDateFromEpochToString(item.dateEpoch, timeZoneId, "E (dd MMM)"),
            conditionText = item.conditionText,
            conditionIcon = "https:${item.conditionIcon}",
            maxMinTemp = getSpannableValueMeasureString(
                buildString {
                    append(
                        item.maxTemp.roundToInt().toString(),
                        "/",
                        item.minTemp.roundToInt().toString()
                    )
                },
                "ºC"
            ),
            maxWindSpeed = getSpannableValueMeasureString(
                item.maxWindSpeed.roundToInt().toString(),
                "kmph"
            ),
            avgHumidity = getSpannableValueMeasureString(
                item.avgHumidity.toString(),
                "%"
            ),
            chanceOfPrecipitation = getSpannableValueMeasureString(
                maxOf(
                    item.chanceOfRain,
                    item.chanceOfSnow
                ).toString(),
                "%"
            )
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
            temp = getSpannableValueMeasureString(
                item.temp.roundToInt().toString(),
                "ºC"
            ),
            tempFeelsLike = item.tempFeelsLike.roundToInt().toString(),
            windSpeed = getSpannableValueMeasureString(
                item.windSpeed.roundToInt().toString(),
                "kmph"
            ),
            windDirection = item.windDirection,
            windGust = item.windGust.roundToInt().toString(),
            humidity = getSpannableValueMeasureString(
                item.humidity.toString(),
                "%"
            ),
            chanceOfPrecipitation = getSpannableValueMeasureString(
                maxOf(
                    item.chanceOfRain,
                    item.chanceOfSnow
                ).toString(),
                "%"
            )
        )
    }

    private fun getTwentyFourHoursForecasts(
        forecastItemList: List<ForecastHourItem>,
        timeZoneId: String
    ): List<ForecastHourUi> {
        val timeNow = ZonedDateTime.now().toEpochSecond()
        val timePlusTwentyFourHours = ZonedDateTime.now().plusHours(24).toEpochSecond()
        return mutableListOf<ForecastHourUi>().apply {
            addAll(forecastItemList.filter {
                it.timeEpoch in timeNow..timePlusTwentyFourHours
            }.map {
                mapForecastHourItemToUi(it, timeZoneId)
            })
        }.toList()
    }

    private fun getSpannableValueMeasureString(
        value: String,
        measure: String
    ): CharSequence {
        val valueSpan = SpannableString(value)
        val measureSpan = SpannableString(measure)
        measureSpan.setSpan(
            TopAlignSuperscriptSpan(),
            0,
            measure.length,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )
        return TextUtils.concat(valueSpan, measureSpan)
    }
}