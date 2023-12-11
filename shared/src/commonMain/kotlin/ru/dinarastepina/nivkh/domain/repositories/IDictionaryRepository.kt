package ru.dinarastepina.nivkh.domain.repositories

import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

interface IDictionaryRepository {
    suspend fun getNivkhWords(limit: Int, offset: Int): List<NivkhWord>

    suspend fun searchNivkhWords(limit: Int, offset: Int, query: String): List<NivkhWord>

    suspend fun getRussianWords(limit: Int, offset: Int): List<RussianWord>

    suspend fun searchRussianWords(limit: Int, offset: Int, query: String): List<RussianWord>
}