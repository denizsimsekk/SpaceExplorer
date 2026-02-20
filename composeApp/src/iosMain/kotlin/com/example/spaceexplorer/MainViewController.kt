package com.example.spaceexplorer

import androidx.compose.ui.window.ComposeUIViewController
import com.example.spaceexplorer.di.ensureKoin

fun MainViewController() = ComposeUIViewController {
    ensureKoin()
    App()
}