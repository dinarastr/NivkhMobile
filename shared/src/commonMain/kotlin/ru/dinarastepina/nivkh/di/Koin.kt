package ru.dinarastepina.nivkh.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.local.cacheModule
import ru.dinarastepina.nivkh.domain.downloader.fileManagerModule
import ru.dinarastepina.nivkh.domain.player.playerModule
import ru.dinarastepina.nivkh.domain.repositories.dataStoreModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModules
        )
    }

fun initKoinForAndroid(appDeclaration: KoinAppDeclaration = {}) =
    initKoin(appDeclaration)

val commonModules = module {
    includes(
        appModule(),
        cacheModule,
        databaseModule,
        localDictionaryModule,
        localPhraseModule,
        dictionaryRepositoryModule,
        phraseRepositoryModule,
        viewModelsModule,
        dataStoreModule,
        playerModule,
        fileManagerModule
    )
}