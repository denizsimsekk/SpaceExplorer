package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Fairings(
    val recovered: Boolean? = null,
    val recovery_attempt: Boolean? = null,
    val reused: Boolean? = null,
    val ships: List<String>? = null
)