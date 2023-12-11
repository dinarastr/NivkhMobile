package ru.dinarastepina.nivkh.domain

import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

interface IPhrasesRepository {
    suspend fun getAllTopics(): Flow<List<Topic>>

    suspend fun getPhrasesByTopic(topic: String): Flow<List<Phrase>>

    suspend fun searchPhrases(query: String): Flow<List<Phrase>>
}