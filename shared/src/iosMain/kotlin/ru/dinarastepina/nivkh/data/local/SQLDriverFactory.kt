package ru.dinarastepina.nivkh.data.local

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent

internal actual val cacheModule: Module = module {
    singleOf(::IosSqlDriverFactory) bind SqlDriverFactory::class
}

class IosSqlDriverFactory : SqlDriverFactory {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, filename: String): SqlDriver {
        val fileManager: NSFileManager = NSFileManager.defaultManager()
        val cacheDir = fileManager.URLsForDirectory(
            NSApplicationSupportDirectory,
            NSUserDomainMask
        ).first()

        val databaseUrl = (cacheDir as NSURL).URLByAppendingPathComponent("databases/$filename")

        val databaseExists = fileManager.fileExistsAtPath(databaseUrl?.path.orEmpty())

        if (!databaseExists) {
            val databasePath = "nivkh.db"
            fileManager.createDirectoryAtPath(
                //create a folder for databases, since sqldelight keeps databases in a folder
                cacheDir.URLByAppendingPathComponent("databases", true)?.path ?: "",
                withIntermediateDirectories = false,
                attributes = null,
                null
            )
            fileManager.copyItemAtPath(srcPath = databasePath, toPath = databaseUrl?.path.orEmpty(), error = null)
        }
        return NativeSqliteDriver(schema.synchronous(), filename)
    }
}