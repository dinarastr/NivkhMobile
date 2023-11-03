package ru.dinarastepina.nivkh.presentation.screens.dictionary.russian

import app.cash.paging.PagingData
import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.presentation.base.State

sealed interface RussianDictionaryState: State {
    data class LoadedState(val words: PagingData<RussianWord>): RussianDictionaryState
}