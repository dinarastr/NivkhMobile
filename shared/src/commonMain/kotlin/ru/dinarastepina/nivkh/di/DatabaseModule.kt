package ru.dinarastepina.nivkh.di

import org.koin.dsl.module
import ru.dinarastepina.database.NivkhDatabase
import ru.dinarastepina.nivkh.data.local.SqlDriverFactory

val databaseModule = module {
    single<NivkhDatabase> {
        val sqlDriverFactory: SqlDriverFactory = get()
        NivkhDatabase(
            driver = sqlDriverFactory.getDriver(
                schema = NivkhDatabase.Schema,
                filename = "nivkh.db"
            )
        )
    }
} 