package com.example.spaceexplorer.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.spaceexplorer.database.SpaceExplorerDatabase

actual fun createInMemoryDriver(): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        SpaceExplorerDatabase.Schema.create(this)
    }
}
