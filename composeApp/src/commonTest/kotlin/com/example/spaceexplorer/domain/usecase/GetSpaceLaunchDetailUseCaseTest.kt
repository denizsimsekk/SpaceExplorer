package com.example.spaceexplorer.domain.usecase

import com.example.spaceexplorer.data.repository.FakeSpaceLaunchesRepository
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class GetSpaceLaunchDetailUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var spaceLaunchesRepository: FakeSpaceLaunchesRepository
    private lateinit var useCase: GetSpaceLaunchDetailUseCase

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        spaceLaunchesRepository = FakeSpaceLaunchesRepository()
        useCase = GetSpaceLaunchDetailUseCase(spaceLaunchesRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `returns launch when repository has launch with id`() = runTest(testDispatcher) {
        val launch = createLaunch(id = "abc", name = "Test Launch")
        spaceLaunchesRepository.launches = listOf(launch)

        val result = useCase("abc")

        assertNotNull(result)
        assertEquals("abc", result?.id)
        assertEquals("Test Launch", result?.name)
    }

    @Test
    fun `returns null when repository has no launch with id`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = listOf(createLaunch(id = "1", name = "Other"))

        val result = useCase("nonexistent")

        assertNull(result)
    }

    @Test
    fun `returns null when repository has empty launches`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = emptyList()

        val result = useCase("any")

        assertNull(result)
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
