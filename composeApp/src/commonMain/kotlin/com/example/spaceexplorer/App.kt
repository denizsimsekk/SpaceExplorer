package com.example.spaceexplorer

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.spaceexplorer.presentation.navigation.AppRoute
import com.example.spaceexplorer.presentation.launcharticle.LaunchArticleScreen
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesScreen
import com.example.spaceexplorer.presentation.spacelaunchdetails.SpaceLaunchDetailScreen
import com.example.spaceexplorer.presentation.spacelaunchdetails.SpaceLaunchDetailsViewModel
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesViewModel
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme() {
        val backStack = remember { mutableStateListOf<AppRoute>(AppRoute.SpaceLaunches) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is AppRoute.SpaceLaunches -> NavEntry(key) {
                        val viewModel = koinInject<SpaceLaunchesViewModel>()
                        SpaceLaunchesScreen(
                            viewModel = viewModel,
                            onLaunchClick = { launch -> backStack.add(AppRoute.LaunchDetails(launch)) }
                        )
                    }

                    is AppRoute.LaunchDetails -> NavEntry(key) {
                        val viewModel = koinInject<SpaceLaunchDetailsViewModel>()
                        SpaceLaunchDetailScreen(
                            spaceLaunchId = key.spaceLaunchId,
                            onBackClick = { backStack.removeLastOrNull() },
                            onUrlClicked = { url ->
                                backStack.add(AppRoute.LaunchArticleScreen(url))
                            },
                            viewModel = viewModel
                        )
                    }

                    is AppRoute.LaunchArticleScreen -> NavEntry(key) {
                        LaunchArticleScreen(
                            url = key.url,
                            onBackClick = { backStack.removeLastOrNull() }
                        )
                    }


                    else -> NavEntry(key) { }
                }
            }
        )
    }
}