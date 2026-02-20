package com.example.spaceexplorer.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.spaceexplorer.database.SpaceExplorerDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = SpaceExplorerDatabase.Schema,
            name = "space_explorer.db"
        )
    }
}
