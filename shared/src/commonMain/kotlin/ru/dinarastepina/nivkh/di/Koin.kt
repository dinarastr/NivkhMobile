package ru.dinarastepina.nivkh.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.dinarastepina.nivkh.data.local.cacheModule
import ru.dinarastepina.nivkh.domain.downloader.fileManagerModule
import ru.dinarastepina.nivkh.domain.player.playerModule
import ru.dinarastepina.nivkh.domain.repositories.dataStoreModule

fun initKoinForAndroid(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule(),
            cacheModule,
            localDictionaryModule,
            localPhraseModule,
            dictionaryRepositoryModule,
            phraseRepositoryModule,
            dataStoreModule,
            playerModule,
            fileManagerModule
        )
    }

fun initKoin(enableNetworkLogs: Boolean) =
    initKoinForAndroid()