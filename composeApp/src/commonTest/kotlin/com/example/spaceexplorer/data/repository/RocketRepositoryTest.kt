package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.model.RocketResponseDto
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.repository.RocketRepository
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RocketRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var api: FakeSpaceExplorerApi
    private lateinit var repository: RocketRepository

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = FakeSpaceExplorerApi()
        repository = RocketRepositoryImpl(api = api)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRocketDetails returns success when api succeeds`() = runTest(testDispatcher) {
        val dto = createRocketDto(id = "falcon-9", name = "Falcon 9", description = "Heavy lift")
        api = FakeSpaceExplorerApi(rockets = mapOf("falcon-9" to dto))
        repository = RocketRepositoryImpl(api = api)

        val result = repository.getRocketDetails("falcon-9")

        assertTrue(result is ResponseState.Success)
        assertEquals("falcon-9", result.data?.id)
        assertEquals("Falcon 9", result.data?.name)
        assertEquals("Heavy lift", result.data?.description)
    }

    @Test
    fun `getRocketDetails returns error when api fails`() = runTest(testDispatcher) {
        api = FakeSpaceExplorerApi(rocketError = RuntimeException("Network error"))
        repository = RocketRepositoryImpl(api = api)

        val result = repository.getRocketDetails("any-id")

        assertTrue(result is ResponseState.Error)
        assertEquals("Network error", result.errorMessage)
    }

    private fun createRocketDto(
        id: String = "rocket-1",
        name: String = "Rocket",
        description: String = "Description"
    ) = RocketResponseDto(
        active = true,
        boosters = 0,
        company = "SpaceX",
        cost_per_launch = 0,
        country = "US",
        description = description,
        first_flight = "2020-01-01",
        id = id,
        name = name,
        stages = 2,
        success_rate_pct = 100,
        type = "rocket",
        wikipedia = "https://example.com"
    )
}
