package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import androidx.paging.PagingData
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events

class RussianDictionaryVM: BaseViewModel<RussianDictionaryState>(
    initialState = RussianDictionaryState.LoadedState(PagingData.empty())
) {
    override fun onEvent(event: Events) {
        when (event) {
            is RussianDictionaryEvents.LoadWords -> getAllRussianWords()
            is RussianDictionaryEvents.SearchWords -> searchRussian(event.query)
        }
    }

    private fun getAllRussianWords() {

    }

    private fun searchRussian(query: String) {

    }
}