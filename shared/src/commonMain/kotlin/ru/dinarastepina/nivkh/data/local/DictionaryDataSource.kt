package ru.dinarastepina.nivkh.data.local

import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

interface DictionaryDataSource {
    suspend fun getAllNivkhWords(limit: Int, offset: Int): List<NivkhWord>

    suspend fun searchNivkhTranslations(limit: Int, offset: Int, query: String): List<Long>

    suspend fun searchNivkhWords(ids: List<Long>): List<NivkhWord>

    suspend fun getAllRussianWords(limit: Int, offset: Int): List<RussianWord>

    suspend fun searchRussianTranslations(limit: Int, offset: Int, query: String): List<Long>

    suspend fun searchRussianWords(ids: List<Long>): List<RussianWord>
}