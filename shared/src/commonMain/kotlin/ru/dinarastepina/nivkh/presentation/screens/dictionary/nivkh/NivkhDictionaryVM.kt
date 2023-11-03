package ru.dinarastepina.nivkh.presentation.screens.dictionary.nivkh

import app.cash.paging.PagingData
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events

class NivkhDictionaryVM: BaseViewModel<NivkhDictionaryState>(
  initialState = NivkhDictionaryState.LoadedState(
      PagingData.empty()
  )
)  {
    override fun onEvent(event: Events) {
        when (event) {
            is NivkhDictionaryEvents.LoadWords -> loadAllWords()
            is NivkhDictionaryEvents.SearchWords -> searchWords(event.query)
        }
    }

    private fun loadAllWords() {

    }

    private fun searchWords(query: String) {

    }
}