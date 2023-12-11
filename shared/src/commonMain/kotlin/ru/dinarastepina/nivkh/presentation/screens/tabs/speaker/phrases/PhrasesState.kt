package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import ru.dinarastepina.nivkh.presentation.base.State
import ru.dinarastepina.nivkh.presentation.models.Phrase

sealed interface PhrasesState: State {

    data object Loading : PhrasesState

    data class LoadedPhrases(
        val phrases: List<Phrase>,
        val playerController: MediaPlayerController,
        val topic: String = "",
        val currentPhrase: Phrase? = null
    ): PhrasesState
}