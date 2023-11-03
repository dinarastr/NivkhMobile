package ru.dinarastepina.nivkh.presentation.screens.dictionary.nivkh

import app.cash.paging.PagingData
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.presentation.base.State

sealed interface NivkhDictionaryState: State {
    data class LoadedState(val words: PagingData<NivkhWord>): NivkhDictionaryState
}