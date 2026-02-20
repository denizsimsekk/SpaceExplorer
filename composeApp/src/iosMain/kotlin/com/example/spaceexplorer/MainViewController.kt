package com.example.spaceexplorer

import androidx.compose.ui.window.ComposeUIViewController
import com.example.spaceexplorer.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}