package ru.dinarastepina.nivkh.data.local

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

//internal actual val cacheModule: Module = module {
//    singleOf(::AndroidSqlDriverFactory) bind SqlDriverFactory::class
//}
class AndroidSqlDriverFactory(private val context: Context): SqlDriverFactory {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, filename: String): SqlDriver {
        val database: File = context.getDatabasePath(filename)

        if (!database.exists()) {
            val inputStream = resource("nivkh.db").readBytes().inputStream()
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(database.absolutePath)
            }

            inputStream.use { input: InputStream ->
                outputStream.use { output: FileOutputStream ->
                    input.copyTo(output)
                }
            }
        }

        return AndroidSqliteDriver(schema.synchronous(), context, filename)
    }
}