package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.remote.SpaceExplorerApi
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow

class SpaceLaunchesRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val database: SpaceExplorerDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SpaceLaunchesRepository, BaseRepository(ioDispatcher = ioDispatcher) {

    override fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchViewEntity>>> {
        return safeApiCall { api.getSpaceLaunches().map { it.toViewEntity() } }
    }

    override suspend fun getSpaceLaunchDetails(id: String): SpaceLaunchViewEntity? {
        return getLaunchesFromLocal()?.find { it.id == id }
    }

    override suspend fun saveLaunchesToLocal(
        launches: List<SpaceLaunchViewEntity>
    ) {
        withContext(ioDispatcher) {
            database.transaction {
                database.spaceLaunchQueries.deleteAll()
                launches.forEach { launch ->
                    database.spaceLaunchQueries.insertOrReplace(
                        id = launch.id,
                        date = launch.date,
                        date_time = launch.dateTime,
                        name = launch.name,
                        success = if (launch.success) 1L else 0L,
                        rocket_id = launch.rocketId,
                        rocket_name = launch.rocketDetails?.name,
                        rocket_description = launch.rocketDetails?.description,
                        article_url = launch.articleUrl,
                        image_url = launch.imageUrl
                    )
                }
            }
        }
    }

    override suspend fun getLaunchesFromLocal(): List<SpaceLaunchViewEntity>? {
        return withContext(ioDispatcher) {
            val rows = database.spaceLaunchQueries.selectAll().executeAsList()
            if (rows.isEmpty()) null
            else rows.map { row ->
                SpaceLaunchViewEntity(
                    id = row.id,
                    date = row.date,
                    dateTime = row.date_time,
                    name = row.name,
                    success = row.success == 1L,
                    rocketId = row.rocket_id,
                    rocketDetails = if (row.rocket_name != null && row.rocket_description != null) {
                        RocketViewEntity(
                            id = row.rocket_id,
                            name = row.rocket_name,
                            description = row.rocket_description
                        )
                    } else null,
                    articleUrl = row.article_url,
                    imageUrl = row.image_url
                )
            }
        }
    }

}