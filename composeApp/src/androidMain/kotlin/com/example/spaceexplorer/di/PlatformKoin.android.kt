package com.example.spaceexplorer.di

import org.koin.core.KoinApplication

actual fun KoinApplication.applyPlatformConfig() {
    // Add androidContext(get()) when you need to inject Android Context
    // and ensure initKoin is called from Application with context
}
