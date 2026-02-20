package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Patch(
    val large: String? = null,
    val small: String? = null
)