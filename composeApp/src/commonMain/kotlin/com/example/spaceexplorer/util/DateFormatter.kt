package com.example.spaceexplorer.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDate(input: String): String {
    if (input.isBlank()) return input
    val instant = Instant.parse(input)
    val date = instant.toLocalDateTime(TimeZone.UTC).date

    return "${date.dayOfMonth.toString().padStart(2, '0')}/${date.monthNumber.toString().padStart(2, '0')}/${date.year}"
}
