package com.example.spaceexplorer.data

import app.cash.sqldelight.db.SqlDriver

expect fun createInMemoryDriver(): SqlDriver
