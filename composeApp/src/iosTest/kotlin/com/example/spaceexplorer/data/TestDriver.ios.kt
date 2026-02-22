package com.example.spaceexplorer.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import co.touchlab.sqliter.createDatabaseManager
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import kotlin.concurrent.AtomicInt

private val dbCounter = AtomicInt(0)

actual fun createInMemoryDriver(): SqlDriver {
    val id = dbCounter.incrementAndGet()
    val schema = SpaceExplorerDatabase.Schema
    val config = DatabaseConfiguration(
        name = "test_$id",
        version = schema.version.toInt(),
        create = { connection ->
            wrapConnection(connection) {
                schema.create(it)
            }
        },
        inMemory = true
    )
    return NativeSqliteDriver(createDatabaseManager(config))
}
