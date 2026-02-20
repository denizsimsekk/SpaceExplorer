package com.example.spaceexplorer.di

import com.example.spaceexplorer.Platform
import com.example.spaceexplorer.data.remote.ApiClient
import com.example.spaceexplorer.data.remote.ApiService
import com.example.spaceexplorer.data.repository.SpaceLaunchesRepositoryImpl
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchesUseCase
import com.example.spaceexplorer.getPlatform
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

private var _koin: Koin? = null

private val networkModule = module {
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

private val platformModule = module {
    single<Platform> { getPlatform() }
}

fun getPlatformModule(): Module = platformModule

fun getAllModules(extraModules: List<Module> = emptyList()): List<Module> =
    listOf(networkModule, platformModule, repositoryModule, useCaseModule, viewModelModule) + extraModules

fun initKoin(extraModules: List<Module> = emptyList()): KoinApplication {
    val app = startKoin {
        applyPlatformConfig()
        modules(getAllModules(extraModules))
    }
    _koin = app.koin
    return app
}

fun ensureKoin(extraModules: List<Module> = emptyList()) {
    if (_koin == null) initKoin(extraModules)
}

val koin: Koin
    get() = _koin
        ?: error("Koin not initialized. Call initKoin() or ensureKoin() before using koin.")
