package ru.dinarastepina.nivkh.data.local

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.database.NivkhDatabase
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

class LocalDataSourceImpl: LocalDataSource, KoinComponent {

    private val db: SqlDriverFactory by inject()

    override suspend fun getAllNivkhWords(limit: Int, offset: Int): List<NivkhWord> {
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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
        val ques = NivkhDatabase(db.getDriver(NivkhDatabase.Schema, "nivkh.db")).nivkhQueries
        return ques
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