package com.example.spaceexplorer.data.model

import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity
import kotlinx.serialization.Serializable

@Serializable
data class SpaceLaunchResponseDto(
    val auto_update: Boolean? = null,
    val capsules: List<String>? = null,
    val cores: List<Core>? = null,
    val crew: List<String>? = null,
    val date_local: String? = null,
    val date_precision: String? = null,
    val date_unix: Int? = null,
    val date_utc: String? = null,
    val details: String? = null,
    val failures: List<Failure>? = null,
    val fairings: Fairings? = null,
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
    fun toViewEntity(): SpaceLaunchesViewEntity {
        return SpaceLaunchesViewEntity(
            auto_update = auto_update ?: false,
            capsules = capsules ?: emptyList(),
            cores = cores ?: emptyList(),
            crew = crew ?: emptyList(),
            date_local = date_local ?: "",
            date_precision = date_precision ?: "",
            date_unix = date_unix ?: 0,
            date_utc = date_utc ?: "",
            details = details ?: "",
            failures = failures ?: emptyList(),
            fairings = fairings ?: Fairings(),
            flight_number = flight_number ?: 0,
            id = id ?: "",
            launch_library_id = launch_library_id ?: "",
            launchpad = launchpad ?: "",
            links = links ?: Links(),
            name = name ?: "",
            net = net ?: false,
            payloads = payloads ?: emptyList(),
            rocket = rocket ?: "",
            ships = ships ?: emptyList(),
            static_fire_date_unix = static_fire_date_unix ?: 0,
            static_fire_date_utc = static_fire_date_utc ?: "",
            success = success ?: false,
            tbd = tbd ?: false,
            upcoming = upcoming ?: false,
            window = window ?: 0
        )
    }
}