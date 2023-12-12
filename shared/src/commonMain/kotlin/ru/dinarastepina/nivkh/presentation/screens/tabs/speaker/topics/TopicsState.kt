package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import ru.dinarastepina.nivkh.presentation.base.State
import ru.dinarastepina.nivkh.presentation.models.Topic

sealed interface TopicsState: State {

    data object Loading: TopicsState
    data class TopicsLoaded(
        val topics: List<Topic> = emptyList()
    ): TopicsState
}