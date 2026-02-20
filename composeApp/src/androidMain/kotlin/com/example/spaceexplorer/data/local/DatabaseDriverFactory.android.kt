package com.example.spaceexplorer.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.spaceexplorer.database.SpaceExplorerDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = SpaceExplorerDatabase.Schema,
            context = context,
            name = "space_explorer.db"
        )
    }
}
