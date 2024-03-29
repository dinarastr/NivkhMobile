package ru.dinarastepina.nivkh.data.local

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import org.koin.core.module.Module
import ru.dinarastepina.database.NivkhDatabase

internal expect val cacheModule : Module

interface SqlDriverFactory {
     suspend fun getDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, filename: String): SqlDriver
}
