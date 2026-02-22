package com.example.spaceexplorer.data.model

import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.util.formatDate
import com.example.spaceexplorer.util.formatDateWithTime
import kotlinx.serialization.Serializable

@Serializable
data class SpaceLaunchResponseDto(
    val auto_update: Boolean? = null,
    val capsules: List<String>? = null,
    val crew: List<String>? = null,
    val date_local: String? = null,
    val date_precision: String? = null,
    val date_unix: Int? = null,
    val date_utc: String? = null,
    val details: String? = null,
    val flight_number: Int? = null,
    val id: String? = null,
    val launch_library_id: String? = null,
    val launchpad: String? = null,
    val links: Links? = null,
    val name: String? = null,
    val net: Boolean? = null,
    val payloads: List<String>? = null,
    val rocket: String? = null,
    val ships: List<String>? = null,
    val static_fire_date_unix: Int? = null,
    val static_fire_date_utc: String? = null,
    val success: Boolean? = null,
    val tbd: Boolean? = null,
    val upcoming: Boolean? = null,
    val window: Int? = null
) {
    fun toViewEntity(): SpaceLaunchViewEntity {
        return SpaceLaunchViewEntity(
            id = id ?: "",
            name = name ?: "",
            success = success ?: false,
            rocketId = rocket ?: "",
            date = formatDate(date_utc ?: ""),
            articleUrl = links?.article ?: links?.wikipedia,
            dateTime = formatDateWithTime(date_utc ?: ""),
            imageUrl = links?.patch?.small
        )
    }
}