package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Flickr(
    val original: List<String>? = null
)