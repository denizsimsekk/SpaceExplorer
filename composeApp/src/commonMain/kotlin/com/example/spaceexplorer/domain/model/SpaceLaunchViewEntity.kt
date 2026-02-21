package com.example.spaceexplorer.domain.model

import com.example.spaceexplorer.data.model.Links
import kotlinx.serialization.Serializable

@Serializable
data class SpaceLaunchViewEntity(
    val date: String,
    val dateTime: String,
    val id: String,
    val links: Links,
    val name: String,
    val success: Boolean,
    val rocketId: String,
    var rocketDetails: RocketViewEntity? = null,
    val articleUrl: String? = null
)