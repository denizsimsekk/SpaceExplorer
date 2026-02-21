package com.example.spaceexplorer.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication

fun initKoin(context: Context): KoinApplication {
    return initKoin {
        androidContext(context)
    }
}
