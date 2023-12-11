package ru.dinarastepina.nivkh.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.repository.DictionaryRepositoryImpl
import ru.dinarastepina.nivkh.domain.repositories.IDictionaryRepository

val repositoryModule: Module = module {
    single<IDictionaryRepository> { DictionaryRepositoryImpl() }
}
