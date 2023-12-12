package ru.dinarastepina.nivkh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.local.PhrasesDataSource
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesRepositoryImpl : IPhrasesRepository, KoinComponent {

    private val phrasesDataSource: PhrasesDataSource by inject()
    override suspend fun getAllTopics(): List<Topic> =
        phrasesDataSource.getAllTopics()

    override suspend fun getPhrasesByTopic(topic: String): List<Phrase> =
        phrasesDataSource.getPhrasesByTopic(topic)

    override suspend fun searchPhrases(query: String): List<Phrase> =
        phrasesDataSource.searchPhrases(query)
}