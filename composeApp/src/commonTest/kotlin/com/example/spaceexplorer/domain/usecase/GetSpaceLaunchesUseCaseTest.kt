package com.example.spaceexplorer.domain.usecase

import com.example.spaceexplorer.data.repository.FakeRocketRepository
import com.example.spaceexplorer.data.repository.FakeSpaceLaunchesRepository
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetSpaceLaunchesUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var spaceLaunchesRepository: FakeSpaceLaunchesRepository
    private lateinit var rocketRepository: FakeRocketRepository
    private lateinit var useCase: GetSpaceLaunchesUseCase

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        spaceLaunchesRepository = FakeSpaceLaunchesRepository()
        rocketRepository = FakeRocketRepository()
        useCase = GetSpaceLaunchesUseCase(spaceLaunchesRepository, rocketRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `returns local data when onRefresh false and local has data`() = runTest(testDispatcher) {
        val localLaunches = listOf(createLaunch(id = "1", name = "Local Launch"))
        spaceLaunchesRepository.launches = localLaunches

        val states = useCase(onRefresh = false).toList()

        assertEquals(1, states.size)
        assertTrue(states.first() is ResponseState.Success)
        assertEquals("Local Launch", (states.first() as ResponseState.Success).data.first().name)
    }

    @Test
    fun `calls api when onRefresh true`() = runTest(testDispatcher) {
        val apiLaunches = listOf(createLaunch(id = "1", name = "API Launch"))
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(apiLaunches)
        )

        val states = useCase(onRefresh = true).toList()

        assertTrue(states.last() is ResponseState.Success)
        assertEquals(1, (states.last() as ResponseState.Success).data.size)
        assertEquals("API Launch", (states.last() as ResponseState.Success).data.first().name)
    }

    @Test
    fun `calls api when onRefresh false but local is empty`() = runTest(testDispatcher) {
        spaceLaunchesRepository.launches = null
        val apiLaunches = listOf(createLaunch(id = "1", name = "API Launch"))
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(apiLaunches)
        )

        val states = useCase(onRefresh = false).toList()

        assertTrue(states.last() is ResponseState.Success)
        assertEquals("API Launch", (states.last() as ResponseState.Success).data.first().name)
    }

    @Test
    fun `fills launches with rocket details from rocket repository`() = runTest(testDispatcher) {
        val apiLaunches = listOf(createLaunch(id = "1", name = "Launch", rocketId = "rocket-1"))
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(apiLaunches)
        )
        rocketRepository.setRocket(createRocket(id = "rocket-1", name = "Falcon 9"))

        val states = useCase(onRefresh = true).toList()


        assertTrue(states[1] is ResponseState.Success)

        val successData = (states[1] as ResponseState.Success).data
        assertTrue(successData.first().rocketDetails != null)
        assertEquals("Falcon 9", successData.first().rocketDetails?.name)
    }

    @Test
    fun `saves launches to local after api success`() = runTest(testDispatcher) {
        val apiLaunches = listOf(createLaunch(id = "1", name = "API Launch"))
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(apiLaunches)
        )
        rocketRepository.setRocket(createRocket(id = "rocket-1"))

        useCase(onRefresh = true).toList()

        assertTrue(spaceLaunchesRepository.saveLaunchesCalledWith != null)
        assertEquals(1, spaceLaunchesRepository.saveLaunchesCalledWith?.size)
        assertEquals("API Launch", spaceLaunchesRepository.saveLaunchesCalledWith?.first()?.name)
    }

    @Test
    fun `emits error when rocket repo returns error and still returns list with null rocket details`() =
        runTest(testDispatcher) {
            val apiLaunches =
                listOf(createLaunch(id = "1", name = "API Launch", rocketId = "rocket-1"))
            spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
                ResponseState.Loading,
                ResponseState.Success(apiLaunches)
            )
            rocketRepository.setRocketError("rocket-1", "Rocket fetch failed")

            val states = useCase(onRefresh = true).toList()

            val errorState = states.find { it is ResponseState.Error }
                ?.let { it as ResponseState.Error }
            assertTrue(errorState != null)
            assertEquals("Rocket fetch failed", errorState.errorMessage)

            assertTrue(states.last() is ResponseState.Success)
            val successState = states.last() as ResponseState.Success
            assertEquals(1, successState.data.size)
            assertNull(successState.data.first().rocketDetails)
        }

    @Test
    fun `emits error when rocket repo returns error`() = runTest(testDispatcher) {
        val apiLaunches = listOf(createLaunch(id = "1", name = "API Launch", rocketId = "rocket-1"))
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Success(apiLaunches)
        )
        rocketRepository.setRocketError("rocket-1", "Rocket fetch failed")

        val states = useCase(onRefresh = true).toList()

        val errorState = states.find { it is ResponseState.Error }
            ?.let { it as ResponseState.Error }
        assertTrue(errorState != null)
        assertEquals("Rocket fetch failed", errorState.errorMessage)
    }

    @Test
    fun `returns error when api fails`() = runTest(testDispatcher) {
        spaceLaunchesRepository.spaceLaunchesFlow = flowOf(
            ResponseState.Loading,
            ResponseState.Error("Network error")
        )

        val states = useCase(onRefresh = true).toList()

        assertTrue(states.last() is ResponseState.Error)
        assertEquals("Network error", (states.last() as ResponseState.Error).errorMessage)
    }

    private fun createLaunch(
        id: String = "id",
        name: String = "Launch",
        rocketId: String = "rocket-1"
    ) = SpaceLaunchViewEntity(
        id = id,
        date = "01/01/2024",
        dateTime = "01/01/2024 12:00",
        name = name,
        success = true,
        rocketId = rocketId,
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
