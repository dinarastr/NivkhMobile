package ru.dinarastepina.nivkh.data.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.local.LocalDataSource
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.domain.IDictionaryRepository

class DictionaryRepositoryImpl: IDictionaryRepository, KoinComponent {

    private val database: LocalDataSource by inject()
    override suspend fun getNivkhWords(limit: Int, offset: Int): List<NivkhWord> {
        return database.getAllNivkhWords(limit, offset)
    }

    override suspend fun searchNivkhWords(limit: Int, offset: Int, query: String): List<NivkhWord> {
        val ids = database.searchNivkhTranslations(limit, offset, query)
        return database.searchNivkhWords(ids)
    }

    override suspend fun getRussianWords(limit: Int, offset: Int): List<RussianWord> {
        return database.getAllRussianWords(limit, offset)
    }

    override suspend fun searchRussianWords(
        limit: Int,
        offset: Int,
        query: String
    ): List<RussianWord> {
        val ids = database.searchRussianTranslations(limit, offset, query)
        return database.searchRussianWords(ids)
    }
}