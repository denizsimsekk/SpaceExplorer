package com.example.spaceexplorer.util

import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeZoneWithName

actual fun formatDate(input: String): String {
    if (input.isBlank()) return input
    return try {
        val instant = Instant.parse(input)
        val date = NSDate.dateWithTimeIntervalSince1970(instant.toEpochMilliseconds() / 1000.0)
        val formatter = NSDateFormatter().apply {
            dateFormat = "dd/MM/yyyy"
            timeZone = NSTimeZone.timeZoneWithName("UTC")!!
            locale = NSLocale.currentLocale
        }
        formatter.stringFromDate(date) ?: input
    } catch (e: Exception) {
        input
    }
}

actual fun formatDateWithTime(input: String): String {
    if (input.isBlank()) return input
    return try {
        val instant = Instant.parse(input)
        val date = NSDate.dateWithTimeIntervalSince1970(instant.toEpochMilliseconds() / 1000.0)
        val formatter = NSDateFormatter().apply {
            dateFormat = "dd/MM/yyyy HH:mm"
            timeZone = NSTimeZone.timeZoneWithName("UTC")!!
            locale = NSLocale.currentLocale
        }
        formatter.stringFromDate(date) ?: input
    } catch (e: Exception) {
        input
    }
}
