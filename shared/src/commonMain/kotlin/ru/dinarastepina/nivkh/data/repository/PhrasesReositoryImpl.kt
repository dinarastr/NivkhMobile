package ru.dinarastepina.nivkh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dinarastepina.nivkh.data.local.PhrasesDataSource
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesRepositoryImpl(
    private val phrasesDataSource: PhrasesDataSource
) : IPhrasesRepository {
    override suspend fun getAllTopics(): List<Topic> =
        phrasesDataSource.getAllTopics()

    override suspend fun getPhrasesByTopic(topic: String): List<Phrase> =
        phrasesDataSource.getPhrasesByTopic(topic)

    override suspend fun searchPhrases(query: String): List<Phrase> =
        phrasesDataSource.searchPhrases(query)
}