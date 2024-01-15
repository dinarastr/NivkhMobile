package ru.dinarastepina.nivkh.data.local

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.ConnectionWrapper
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.dataWithBytes
import platform.Foundation.stringByAppendingPathComponent

internal actual val cacheModule: Module = module {
    singleOf(::IosSqlDriverFactory) bind SqlDriverFactory::class
}

class IosSqlDriverFactory : SqlDriverFactory {
    @OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class, BetaInteropApi::class)
    override suspend fun getDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, filename: String): SqlDriver {
        val fileManager: NSFileManager = NSFileManager.defaultManager()
        val databaseCache = NSSearchPathForDirectoriesInDomains(
            directory = NSApplicationSupportDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        ).first() as NSString

        val dbDirectoryPath = databaseCache.stringByAppendingPathComponent("databases")
        val targetDBPath = databaseCache.stringByAppendingPathComponent("databases/$filename")

        val directoryExists = fileManager.fileExistsAtPath(dbDirectoryPath)
        val databaseExists = fileManager.fileExistsAtPath(targetDBPath)
        if (!databaseExists) {
            memScoped {

                val error: ObjCObjectVar<NSError?> = alloc()

                if (directoryExists.not()) {
                    val createSuccess = fileManager.createDirectoryAtPath(
                        path = dbDirectoryPath,
                        withIntermediateDirectories = true,
                        attributes = null,
                        error = error.ptr
                    )
                }

                val copySuccess = fileManager.createFileAtPath(
                    path = targetDBPath,
                    contents = resource("source.db").readBytes().toNSData(),
                    attributes = null
                )

                println(error.toString())

            }
        }

        println(databaseCache)
        return NativeSqliteDriver(schema = schema.synchronous(), filename)
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData() = this.usePinned {
    NSData.create(bytes = it.addressOf(0), length = this.size.convert())
}
