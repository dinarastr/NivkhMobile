package ru.dinarastepina.nivkh.data.local

import ru.dinarastepina.database.NivkhDatabase
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

class LocalDataSourceImpl(
    private val database: NivkhDatabase
): DictionaryDataSource {

    override suspend fun getAllNivkhWords(limit: Int, offset: Int): List<NivkhWord> {
        return database.nivkhQueries
            .selectAllNivkhWords(
                limit.toLong(),
                offset.toLong()
            )
            .executeAsList()
            .map {
                mapToNivkh(
                    it.id.toInt(),
                    it.wordId.toInt(),
                    it.type,
                    it.content.orEmpty()
                )
            }
    }

    override suspend fun searchNivkhTranslations(
        limit: Int,
        offset: Int,
        query: String
    ): List<Long> {
        return database.nivkhQueries
            .searchNivkhTranslations(
                query,
                limit.toLong(),
                offset.toLong()
            )
            .executeAsList()
    }

    override suspend fun searchNivkhWords(
        ids: List<Long>
    ): List<NivkhWord> {
        return database.nivkhQueries
            .searchNivkhWords(
                ids,
            )
            .executeAsList().map {
            mapToNivkh(
                it.id.toInt(),
                it.wordId.toInt(),
                it.type,
                it.content.orEmpty()
            )
        }
    }

    override suspend fun getAllRussianWords(limit: Int, offset: Int): List<RussianWord> {
        return database.nivkhQueries
            .selectAllRussianWords(
                limit.toLong(),
                offset.toLong()
            )
            .executeAsList()
            .map {
                mapToRussian(
                    it.id.toInt(),
                    it.wordId.toInt(),
                    it.type,
                    it.content
                )
            }
    }

    override suspend fun searchRussianTranslations(
        limit: Int,
        offset: Int,
        query: String
    ): List<Long> {
        return database.nivkhQueries
            .searchRussianTranslations(
                query,
                limit.toLong(),
                offset.toLong()
            )
            .executeAsList()
    }

    override suspend fun searchRussianWords(
        ids: List<Long>
    ): List<RussianWord> {
        return database.nivkhQueries
            .searchRussianWords(
                ids,
            )
            .executeAsList().map {
                mapToRussian(
                    it.id.toInt(),
                    it.wordId.toInt(),
                    it.type,
                    it.content
                )
            }
    }
}

fun mapToNivkh(
    id: Int,
    wordId: Int,
    type: String,
    content: String
) = NivkhWord(
    id, wordId, type, content
)

fun mapToRussian(
    id: Int,
    wordId: Int,
    type: String,
    content: String
) = RussianWord(
    id, wordId, type, content
)
