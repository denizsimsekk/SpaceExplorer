package com.example.spaceexplorer

import android.app.Application
import com.example.spaceexplorer.di.initKoin

class SpaceExplorerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this@SpaceExplorerApplication)
    }
}
