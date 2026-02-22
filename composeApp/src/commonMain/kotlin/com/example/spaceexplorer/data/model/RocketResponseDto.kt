package com.example.spaceexplorer.data.model

import com.example.spaceexplorer.domain.model.RocketViewEntity
import kotlinx.serialization.Serializable

@Serializable
data class RocketResponseDto(
    val active: Boolean,
    val boosters: Int,
    val company: String,
    val cost_per_launch: Int,
    val country: String,
    val description: String,
    val first_flight: String,
    val id: String,
    val name: String,
    val stages: Int,
    val success_rate_pct: Int,
    val type: String,
    val wikipedia: String
) {
    fun toViewEntity(): RocketViewEntity {
        return RocketViewEntity(
            description = description,
            id = id,
            name = name
        )
    }
}