package com.example.spaceexplorer.presentation.spacelaunches

import com.example.spaceexplorer.data.repository.FakeRocketRepository
import com.example.spaceexplorer.data.repository.FakeSpaceLaunchesRepository
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceLaunchesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var spaceLaunchesRepository: FakeSpaceLaunchesRepository
    private lateinit var rocketRepository: FakeRocketRepository
    private lateinit var useCase: GetSpaceLaunchesUseCase
    private lateinit var viewModel: SpaceLaunchesViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        spaceLaunchesRepository = FakeSpaceLaunchesRepository().apply {
            launches = null
            spaceLaunchesFlow = flowOf(ResponseState.Loading, ResponseState.Success(emptyList()))
        }
        rocketRepository = FakeRocketRepository()
        useCase = GetSpaceLaunchesUseCase(spaceLaunchesRepository, rocketRepository)
        viewModel = SpaceLaunchesViewModel(useCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSpaceLaunches emits success when use case returns data`() = runTest(testDispatcher) {
        val launch = createLaunch(id = "1", name = "Launch One")
        spaceLaunchesRepository.launches = null
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(listOf(launch))
        )
        rocketRepository.setRocket(createRocket(id = "rocket-1"))

        viewModel.getSpaceLaunches(onRefresh = false)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.spaceLaunches.size)
        assertEquals("Launch One", state.spaceLaunches[0].name)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `getSpaceLaunches emits error when use case returns error`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = null
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Error("Network error")
        )

        viewModel.getSpaceLaunches(onRefresh = false)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Network error", state.errorMessage)
        assertTrue(state.spaceLaunches.isEmpty())
    }

    @Test
    fun `getSpaceLaunches sets loading on start`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = null
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(emptyList())
        )

        viewModel.getSpaceLaunches(onRefresh = false)
        assertTrue(viewModel.uiState.value.isLoading)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `getSpaceLaunches with refresh sets refreshing flag`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = null
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(emptyList())
        )

        viewModel.getSpaceLaunches(onRefresh = true)
        assertTrue(viewModel.uiState.value.isRefreshing)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isRefreshing)
    }

    private fun createLaunch(
        id: String = "id",
        name: String = "Launch",
    ) = SpaceLaunchViewEntity(
        id = id,
        date = "01/01/2024",
        dateTime = "01/01/2024 12:00",
        name = name,
        success = true,
        rocketId = "rocket-1",
        rocketDetails = null,
        articleUrl = "https://example.com",
        imageUrl = "https://example.com/img.png"
    )

    private fun createRocket(
        id: String = "rocket-1",
        name: String = "Falcon"
    ) = RocketViewEntity(
        id = id,
        name = name,
        description = "Rocket description"
    )
}
