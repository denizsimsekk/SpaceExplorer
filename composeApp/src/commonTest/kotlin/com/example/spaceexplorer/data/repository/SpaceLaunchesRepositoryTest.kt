package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.createInMemoryDriver
import com.example.spaceexplorer.data.model.SpaceLaunchResponseDto
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.resetMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceLaunchesRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var database: SpaceExplorerDatabase
    private lateinit var api: FakeSpaceExplorerApi
    private lateinit var repository: SpaceLaunchesRepository

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val driver = createInMemoryDriver()
        database = SpaceExplorerDatabase(driver)
        api = FakeSpaceExplorerApi()
        repository = SpaceLaunchesRepositoryImpl(api, database, testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSpaceLaunches returns success when api succeeds`() = runTest(testDispatcher) {
        val dto = createDto(id = "1", name = "API Launch")
        api = FakeSpaceExplorerApi(launches = listOf(dto))
        repository = SpaceLaunchesRepositoryImpl(api, database, testDispatcher)

        val states = repository.getSpaceLaunches().toList()

        assertTrue(states.first() is ResponseState.Loading)
        val result = states.last()
        assertTrue(result is ResponseState.Success)
        assertEquals(1, result.data.size)
        assertEquals("1", result.data.first().id)
        assertEquals("API Launch", result.data.first().name)
    }

    @Test
    fun `getSpaceLaunches returns error when api fails`() = runTest(testDispatcher) {
        api = FakeSpaceExplorerApi(launchesError = RuntimeException("Network error"))
        repository = SpaceLaunchesRepositoryImpl(api, database, testDispatcher)

        val states = repository.getSpaceLaunches().toList()

        val result = states.last()
        assertTrue(result is ResponseState.Error)
        assertEquals("Network error", result.errorMessage)
    }

    @Test
    fun `getLaunchesFromLocal returns null when database empty`() = runTest(testDispatcher) {
        val result = repository.getLaunchesFromLocal()
        assertNull(result)
    }

    @Test
    fun `saveLaunchesToLocal and getLaunchesFromLocal persist and retrieve`() =
        runTest(testDispatcher) {
            val launches = listOf(
                createLaunch(id = "1", name = "Launch 1"),
                createLaunch(id = "2", name = "Launch 2", withRocket = true)
            )
            repository.saveLaunchesToLocal(launches)
            advanceUntilIdle()

            val result = repository.getLaunchesFromLocal()
            assertNotNull(result)
            assertEquals(2, result.size)
            assertEquals("Launch 1", result[0].name)
            assertEquals("Launch 2", result[1].name)
            assertNull(result[0].rocketDetails)
            assertNotNull(result[1].rocketDetails)
        }

    @Test
    fun `getSpaceLaunchDetails returns correct launch by id`() = runTest(testDispatcher) {
        val launch = createLaunch(id = "abc", name = "Test Launch", withRocket = true)
        repository.saveLaunchesToLocal(listOf(launch))
        advanceUntilIdle()

        val result = repository.getSpaceLaunchDetails("abc")
        assertNotNull(result)
        assertEquals("abc", result.id)
        assertEquals("Test Launch", result.name)
    }

    @Test
    fun `getSpaceLaunchDetails returns null when not found`() = runTest(testDispatcher) {
        repository.saveLaunchesToLocal(listOf(createLaunch(id = "1", name = "Launch")))
        advanceUntilIdle()

        val result = repository.getSpaceLaunchDetails("nonexistent")
        assertNull(result)
    }

    @Test
    fun `saveLaunchesToLocal replaces existing launches`() = runTest(testDispatcher) {
        repository.saveLaunchesToLocal(listOf(createLaunch(id = "1", name = "First")))
        advanceUntilIdle()
        repository.saveLaunchesToLocal(listOf(createLaunch(id = "2", name = "Second")))
        advanceUntilIdle()

        val result = repository.getLaunchesFromLocal()
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("Second", result[0].name)
    }

    private fun createLaunch(
        id: String = "id",
        name: String = "Launch",
        withRocket: Boolean = false
    ) = SpaceLaunchViewEntity(
        id = id,
        date = "01/01/2024",
        dateTime = "01/01/2024 12:00",
        name = name,
        success = true,
        rocketId = "rocket-1",
        rocketDetails = if (withRocket) {
            RocketViewEntity(id = "rocket-1", name = "Falcon", description = "Rocket desc")
        } else null,
        articleUrl = "https://example.com",
        imageUrl = "https://example.com/img.png"
    )

    private fun createDto(id: String, name: String) = SpaceLaunchResponseDto(
        id = id,
        name = name,
        date_utc = "2024-01-01T12:00:00.000Z",
        success = true,
        rocket = "rocket-1"
    )
}
