package ru.dinarastepina.nivkh.data.local

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.database.NivkhDatabase
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.models.Topic

class PhrasesDataSourceImpl: PhrasesDataSource, KoinComponent {

    private val db: SqlDriverFactory by inject()

    override suspend fun getAllTopics(): List<Topic> {
        val ques = NivkhDatabase(
            db.getDriver(
                NivkhDatabase.Schema,
                "source.db")).phrasesQueries

        return ques
            .getAllTopics()
            .executeAsList()
            .map {
                mapToTopic(it.topic, it.imageUrl ?: "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/images%2Fcooking%403x.png?alt=media&token=c3a2a5a3-342e-42a4-afed-8e13c2e51559")
            }
    }

    override suspend fun getPhrasesByTopic(topic: String): List<Phrase> {
        val ques = NivkhDatabase(
            db.getDriver(
                NivkhDatabase.Schema,
                "nivkh.db")).phrasesQueries

        return ques
            .getPhrasesByTopic(topic)
            .executeAsList()
            .map {
                mapToPhrase(it.nivkh, it.russian, it.audio, it.topic)
            }
    }

    override suspend fun searchPhrases(query: String): List<Phrase> {
        val ques = NivkhDatabase(
            db.getDriver(
                NivkhDatabase.Schema,
                "nivkh.db")).phrasesQueries

        return ques
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
