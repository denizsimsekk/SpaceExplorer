package com.example.spaceexplorer.di

import org.koin.core.KoinApplication

actual fun KoinApplication.applyPlatformConfig() {
    // iOS has no additional Koin config
}