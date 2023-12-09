package ru.dinarastepina.nivkh.domain

import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

interface IPhrasesRepository {
    suspend fun getAllTopics(): List<Topic>

    suspend fun getPhrasesByTopic(topic: String): List<Phrase>
}