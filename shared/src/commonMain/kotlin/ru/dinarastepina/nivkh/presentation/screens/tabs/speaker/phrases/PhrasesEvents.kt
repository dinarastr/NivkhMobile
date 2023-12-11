package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import ru.dinarastepina.nivkh.presentation.base.Events
import ru.dinarastepina.nivkh.presentation.models.Phrase

sealed class PhrasesEvents: Events {
    data class LoadPhrases(val topic: String) : PhrasesEvents()
    data class StartAudio(val phrase: Phrase): PhrasesEvents()

    data class CheckIfCached(val url: String): PhrasesEvents()

    data object StopAudio: PhrasesEvents()

    data class DownloadFile(val url: String): PhrasesEvents()
}