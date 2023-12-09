package ru.dinarastepina.nivkh.data.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.local.PhrasesDataSource
import ru.dinarastepina.nivkh.domain.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesReositoryImpl: IPhrasesRepository, KoinComponent {

    val phrasesDataSource: PhrasesDataSource by inject()
    override suspend fun getAllTopics(): List<Topic> {
        return phrasesDataSource.getAllTopics()
    }

    override suspend fun getPhrasesByTopic(topic: String): List<Phrase> {
        return phrasesDataSource.getPhrasesByTopic(topic)
    }
}