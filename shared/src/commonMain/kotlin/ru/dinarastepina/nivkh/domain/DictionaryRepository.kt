package ru.dinarastepina.nivkh.domain

import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

interface DictionaryRepository {
    suspend fun getNivkhWords(limit: Int, offset: Int): List<NivkhWord>

    suspend fun searchNivkhWords(limit: Int, offset: Int, query: String): List<NivkhWord>

    suspend fun getRussianWords(limit: Int, offset: Int): List<RussianWord>

    suspend fun searchRussianWords(limit: Int, offset: Int, query: String): List<RussianWord>
}