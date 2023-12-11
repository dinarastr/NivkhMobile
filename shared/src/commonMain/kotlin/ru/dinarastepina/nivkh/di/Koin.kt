package ru.dinarastepina.nivkh.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.dinarastepina.nivkh.data.local.cacheModule
import ru.dinarastepina.nivkh.domain.repositories.dataStoreModule

fun initKoinForAndroid(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule(),
            localModule,
            cacheModule,
            localModule,
            repositoryModule,
            dataStoreModule
        )
    }

fun initKoin(enableNetworkLogs: Boolean) =
    initKoinForAndroid()