package nik.borisov.weather.utils

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

interface TimeConverter {

    fun convertTimeDateFromEpochToString(time: Long, timeZoneId: String, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(Date(time * 1000))
    }

    fun getCurrentDayEpoch(): Long {
        return ZonedDateTime.now().toEpochSecond() / 86400 * 86400
    }

    fun getCurrentHourEpoch(): Long {
        return ZonedDateTime.now().toEpochSecond() / 3600 * 3600
    }
}