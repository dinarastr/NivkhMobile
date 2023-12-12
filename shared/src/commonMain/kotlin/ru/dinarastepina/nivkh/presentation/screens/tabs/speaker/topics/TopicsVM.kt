package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.models.Topic

class TopicsVM : BaseViewModel<TopicsState, TopicsEvents>(
    initialState = TopicsState.Loading
), KoinComponent {

    private val repository: IPhrasesRepository by inject()
    override fun onEvent(event: TopicsEvents) {
        when (event) {
            is TopicsEvents.LoadTopics -> loadTopics()
        }
    }

    private fun loadTopics() {
        coroutineScope.launch(Dispatchers.Default) {
            mutableState.update {
                TopicsState.TopicsLoaded(
                    topics = repository.getAllTopics()
                )
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        coroutineScope.cancel()
    }
}