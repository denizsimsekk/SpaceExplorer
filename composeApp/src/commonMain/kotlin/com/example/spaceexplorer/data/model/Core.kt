package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Core(
    val core: String? = null,
    val flight: Int? = null,
    val gridfins: Boolean? = null,
    val landing_attempt: Boolean? = null,
    val landing_success: Boolean? = null,
    val landing_type: String? = null,
    val landpad: String? = null,
    val legs: Boolean? = null,
    val reused: Boolean? = null
)