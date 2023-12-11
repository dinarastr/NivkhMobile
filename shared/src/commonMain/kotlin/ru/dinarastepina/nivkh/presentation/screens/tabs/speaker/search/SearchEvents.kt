package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.search

import ru.dinarastepina.nivkh.presentation.models.Phrase

sealed class SearchEvents {
    data class SearchWords(val query: String) : SearchEvents()
    data class StartAudio(val phrase: Phrase): SearchEvents()

    data class CheckIfCached(val url: String): SearchEvents()

    data object StopAudio: SearchEvents()

    data class DownloadFile(val url: String): SearchEvents()
}

