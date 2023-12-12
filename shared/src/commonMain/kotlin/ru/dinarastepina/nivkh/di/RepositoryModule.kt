package ru.dinarastepina.nivkh.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.dinarastepina.nivkh.data.repository.DictionaryRepositoryImpl
import ru.dinarastepina.nivkh.data.repository.PhrasesRepositoryImpl
import ru.dinarastepina.nivkh.domain.repositories.IDictionaryRepository
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository

val dictionaryRepositoryModule: Module = module {
    single<IDictionaryRepository> { DictionaryRepositoryImpl() }
}

val phraseRepositoryModule: Module = module {
    single<IPhrasesRepository> { PhrasesRepositoryImpl() }
}
