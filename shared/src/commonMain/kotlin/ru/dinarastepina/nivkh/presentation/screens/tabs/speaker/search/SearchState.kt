package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.search

import ru.dinarastepina.nivkh.presentation.base.State
import ru.dinarastepina.nivkh.presentation.models.Phrase

sealed interface SearchState: State {

    data object Empty: SearchState
    data class Success(
        val phrases: List<Phrase>,
        val playerController: MediaPlayerController,
        val query: String = "",
        val currentPhrase: Phrase? = null
    ) : SearchState
}