package ru.dinarastepina.nivkh.di

import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.local.DictionaryDataSource
import ru.dinarastepina.nivkh.data.local.LocalDataSourceImpl
import ru.dinarastepina.nivkh.data.local.PhrasesDataSource
import ru.dinarastepina.nivkh.data.local.PhrasesDataSourceImpl

val localDictionaryModule = module {
    single<DictionaryDataSource> { LocalDataSourceImpl() }
}

val localPhraseModule = module {
    single<PhrasesDataSource> { PhrasesDataSourceImpl() }
}