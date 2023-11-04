package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.presentation.base.State

sealed interface NivkhDictionaryState: State {
    data class LoadedState(
        val query: String = "",
        val words: Flow<PagingData<NivkhWord>>
    ): NivkhDictionaryState
}