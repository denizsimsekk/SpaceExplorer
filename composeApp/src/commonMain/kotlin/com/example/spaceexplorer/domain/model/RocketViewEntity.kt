package com.example.spaceexplorer.domain.model

data class RocketViewEntity(
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
)
