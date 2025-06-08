package ru.dinarastepina.nivkh.data.local

import ru.dinarastepina.database.NivkhDatabase
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesDataSourceImpl(
    private val database: NivkhDatabase
): PhrasesDataSource {

    override suspend fun getAllTopics(): List<Topic> {
        return database.phrasesQueries
            .getAllTopics()
            .executeAsList()
            .map {
                mapToTopic(it.topic, it.imageUrl ?: "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/images%2Fcooking%403x.png?alt=media&token=c3a2a5a3-342e-42a4-afed-8e13c2e51559")
            }
    }

    override suspend fun getPhrasesByTopic(topic: String): List<Phrase> {
        return database.phrasesQueries
            .getPhrasesByTopic(topic)
            .executeAsList()
            .map {
                mapToPhrase(it.nivkh, it.russian, it.audio, it.topic)
            }
    }

    override suspend fun searchPhrases(query: String): List<Phrase> {
        return database.phrasesQueries
            .searchPhrases(query)
            .executeAsList()
            .map {
                mapToPhrase(it.nivkh, it.russian, it.audio, it.topic)
            }
    }
}

fun mapToTopic(title: String, image: String) = Topic(title, image)

fun mapToPhrase(
    nivkh: String,
    russian: String,
    audio: String,
    topic: String
) = Phrase(nivkh, russian, audio, topic)
