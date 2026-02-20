package com.example.spaceexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val article: String? = null,
    val flickr: Flickr? = null,
    val patch: Patch? = null,
    val presskit: String? = null,
    val reddit: Reddit? = null,
    val webcast: String? = null,
    val wikipedia: String? = null,
    val youtube_id: String? = null
)