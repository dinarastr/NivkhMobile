package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events

class TopicsVM: BaseViewModel<TopicsState>(
    initialState = TopicsState.TopicsLoaded()
), KoinComponent {

    private val repository: IPhrasesRepository by inject()
    override fun onEvent(event: Events) {
        when (event) {
            is TopicsEvents.LoadTopics -> loadTopics()
        }
    }

    private fun loadTopics() {
        TODO("Not yet implemented")
    }
}