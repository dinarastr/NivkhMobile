package ru.dinarastepina.nivkh.data.local

import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

interface PhrasesDataSource {
    suspend fun getAllTopics(): List<Topic>

    suspend fun getPhrasesByTopic(topic: String): List<Phrase>
}