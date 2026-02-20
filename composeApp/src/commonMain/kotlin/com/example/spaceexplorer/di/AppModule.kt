package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.DatabaseDriverFactory
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import com.example.spaceexplorer.Platform
import com.example.spaceexplorer.data.remote.ApiClient
import com.example.spaceexplorer.data.remote.ApiService
import com.example.spaceexplorer.data.repository.SpaceLaunchesRepositoryImpl
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchesUseCase
import com.example.spaceexplorer.getPlatform
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient.httpClient }
    single { ApiService(get()) }
}

private val repositoryModule = module {
    single<SpaceLaunchesRepository> { SpaceLaunchesRepositoryImpl(get()) }
}

private val useCaseModule = module {
    factory<GetSpaceLaunchesUseCase> { GetSpaceLaunchesUseCase(get()) }
}

private val viewModelModule = module {
    factory<SpaceLaunchesViewModel> { SpaceLaunchesViewModel(get()) }
}

private val platformCoreModule = module {
    single<Platform> { getPlatform() }
}

private val databaseModule = module {
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        SpaceExplorerDatabase(driver)
    }
}

expect fun platformModule(): Module

val appModules = listOf(
    platformModule(),
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    platformCoreModule,
    databaseModule
)
