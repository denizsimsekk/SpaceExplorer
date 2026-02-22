package com.example.spaceexplorer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RocketViewEntity(
    val description: String,
    val id: String,
    val name: String,
)
