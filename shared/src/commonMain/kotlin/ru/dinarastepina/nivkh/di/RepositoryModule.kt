package ru.dinarastepina.nivkh.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.repository.DictionaryRepositoryImpl
import ru.dinarastepina.nivkh.domain.DictionaryRepository

val repositoryModule: Module = module {
    single<DictionaryRepository> { DictionaryRepositoryImpl() }
}
