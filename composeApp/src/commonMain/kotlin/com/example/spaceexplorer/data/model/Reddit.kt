package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Reddit(
    val campaign: String? = null,
    val launch: String? = null,
    val media: String? = null,
    val recovery: String? = null
)