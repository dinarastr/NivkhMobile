package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel

class TopicsVM(
    private val repository: IPhrasesRepository
) : BaseViewModel<TopicsState, TopicsEvents>(
    initialState = TopicsState.Loading
) {

    override fun onEvent(event: TopicsEvents) {
        when (event) {
            is TopicsEvents.LoadTopics -> loadTopics()
        }
    }

    private fun loadTopics() {
        screenModelScope.launch(Dispatchers.Default) {
            mutableState.update {
                TopicsState.TopicsLoaded(
                    topics = repository.getAllTopics()
                )
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        screenModelScope.cancel()
    }
}