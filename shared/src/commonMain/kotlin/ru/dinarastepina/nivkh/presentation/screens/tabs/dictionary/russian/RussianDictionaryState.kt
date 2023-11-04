package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.presentation.base.State

sealed interface RussianDictionaryState: State {
    data class LoadedState(
        val query: String = "",
        val words: Flow<PagingData<RussianWord>>
    ): RussianDictionaryState
}