package ru.dinarastepina.nivkh.data.local

import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

class LocalDataSourceImpl: LocalDataSource {
    override suspend fun getAllNivkhWords(limit: Int, offset: Int): List<NivkhWord> {
        TODO("Not yet implemented")
    }

    override suspend fun searchNivkhTranslations(
        limit: Int,
        offset: Int,
        query: String
    ): List<NivkhWord> {
        TODO("Not yet implemented")
    }

    override suspend fun searchNivkhWords(
        limit: Int,
        offset: Int,
        ids: List<Int>
    ): List<NivkhWord> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRussianWords(limit: Int, offset: Int): List<RussianWord> {
        TODO("Not yet implemented")
    }

    override suspend fun searchRussianTranslations(
        limit: Int,
        offset: Int,
        query: String
    ): List<RussianWord> {
        TODO("Not yet implemented")
    }

    override suspend fun searchRussianWords(
        limit: Int,
        offset: Int,
        ids: List<Int>
    ): List<RussianWord> {
        TODO("Not yet implemented")
    }
}