package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Failure(
    val altitude: Int? = null,
    val reason: String? = null,
    val time: Int? = null
)