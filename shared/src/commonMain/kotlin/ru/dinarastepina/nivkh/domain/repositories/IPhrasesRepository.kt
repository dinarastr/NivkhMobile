package ru.dinarastepina.nivkh.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

interface IPhrasesRepository {
    suspend fun getAllTopics(): List<Topic>

    suspend fun getPhrasesByTopic(topic: String): List<Phrase>

    suspend fun searchPhrases(query: String): List<Phrase>
}