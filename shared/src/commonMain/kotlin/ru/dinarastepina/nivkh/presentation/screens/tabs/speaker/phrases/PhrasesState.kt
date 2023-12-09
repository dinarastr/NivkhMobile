package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import ru.dinarastepina.nivkh.presentation.base.State
import ru.dinarastepina.nivkh.presentation.models.Phrase

sealed interface PhrasesState: State {
    data class LoadedPhrases(
        val phrases: List<Phrase> = emptyList()
    ): PhrasesState
}