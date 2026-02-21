package com.example.spaceexplorer.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun formatDate(input: String): String {
    if (input.isBlank()) return input
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(input) ?: return input
        outputFormat.format(date)
    } catch (e: Exception) {
        input
    }
}

actual fun formatDateWithTime(input: String): String {
    if (input.isBlank()) return input
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(input) ?: return input
        outputFormat.format(date)
    } catch (e: Exception) {
        input
    }
}
