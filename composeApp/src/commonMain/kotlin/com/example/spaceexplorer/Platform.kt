package com.example.spaceexplorer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform