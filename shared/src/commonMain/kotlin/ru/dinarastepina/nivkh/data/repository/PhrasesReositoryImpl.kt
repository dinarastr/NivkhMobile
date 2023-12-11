package ru.dinarastepina.nivkh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.local.PhrasesDataSource
import ru.dinarastepina.nivkh.domain.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesRepositoryImpl : IPhrasesRepository, KoinComponent {

    private val phrasesDataSource: PhrasesDataSource by inject()
    override suspend fun getAllTopics(): Flow<List<Topic>> = flow {
        phrasesDataSource.getAllTopics()
    }

    override suspend fun getPhrasesByTopic(topic: String): Flow<List<Phrase>> = flow {
        phrasesDataSource.getPhrasesByTopic(topic)
    }

    override suspend fun searchPhrases(query: String): Flow<List<Phrase>> = flow{
        phrasesDataSource.searchPhrases(query)
    }
}