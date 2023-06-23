package nik.borisov.weather.utils

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

interface DateTimeHelper {

    fun convertTimeDateFromEpochToString(time: Long, timeZoneId: String, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(Date(time * MILLISECONDS_IN_SECONDS))
    }

    fun getCurrentDayEpoch(): Long {
        return ZonedDateTime.now().toEpochSecond() / SECONDS_IN_DAY * SECONDS_IN_DAY
    }

    fun getCurrentHourEpoch(): Long {
        return ZonedDateTime.now().toEpochSecond() / SECONDS_IN_HOUR * SECONDS_IN_HOUR
    }

    companion object {

        private const val SECONDS_IN_HOUR = 3600
        private const val SECONDS_IN_DAY = 86400
        private const val MILLISECONDS_IN_SECONDS = 1000
    }
}