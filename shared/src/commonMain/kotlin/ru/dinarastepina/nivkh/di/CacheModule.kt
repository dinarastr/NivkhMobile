package ru.dinarastepina.nivkh.di

import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.local.LocalDataSource
import ru.dinarastepina.nivkh.data.local.LocalDataSourceImpl

val localModule = module {
    single<LocalDataSource> { LocalDataSourceImpl() }
}