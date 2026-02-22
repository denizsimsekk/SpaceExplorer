package com.example.spaceexplorer.presentation.spacelaunchdetails

import com.example.spaceexplorer.data.repository.FakeSpaceLaunchesRepository
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceLaunchDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var spaceLaunchesRepository: FakeSpaceLaunchesRepository
    private lateinit var useCase: GetSpaceLaunchDetailUseCase
    private lateinit var viewModel: SpaceLaunchDetailsViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        spaceLaunchesRepository = FakeSpaceLaunchesRepository().apply { launches = null }
        useCase = GetSpaceLaunchDetailUseCase(spaceLaunchesRepository)
        viewModel = SpaceLaunchDetailsViewModel(useCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSpaceLaunchDetail updates state when use case returns data`() = runTest(testDispatcher) {
        val launch = createLaunch(id = "abc", name = "Test Launch")
        spaceLaunchesRepository.launches = listOf(launch)

        viewModel.getSpaceLaunchDetail("abc")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertNotNull(state.spaceLaunchViewEntity)
        assertEquals("abc", state.spaceLaunchViewEntity?.id)
        assertEquals("Test Launch", state.spaceLaunchViewEntity?.name)
    }

    @Test
    fun `getSpaceLaunchDetail updates state to null when use case returns null`() = runTest(testDispatcher) {
        viewModel.getSpaceLaunchDetail("unknown")
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.spaceLaunchViewEntity)
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
}
