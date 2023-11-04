package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events
import ru.dinarastepina.nivkh.presentation.paging.NivkhPagingSource

class NivkhDictionaryVM: BaseViewModel<NivkhDictionaryState>(
  initialState = NivkhDictionaryState.LoadedState(
      "",
      flowOf(PagingData.empty())
  )
)  {
    override fun onEvent(event: Events) {
        when (event) {
            is NivkhDictionaryEvents.LoadWords -> loadAllWords()
            is NivkhDictionaryEvents.SearchWords -> searchWords(event.query)
            is NivkhDictionaryEvents.ClearSearch -> loadAllWords()
        }
    }

    private val defaultPager: Pager<Int, NivkhWord> = kotlin.run {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        check(pagingConfig.pageSize == pagingConfig.initialLoadSize) {
            "As SqlDelight uses offset based pagination, an elegant PagingSource implementation requires each page to be of equal size."
        }
        Pager(pagingConfig) {
            NivkhPagingSource("")
        }
    }

    private fun getSearchPager(query: String): Pager<Int, NivkhWord> = kotlin.run {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        check(pagingConfig.pageSize == pagingConfig.initialLoadSize) {
            "As SqlDelight uses offset based pagination, an elegant PagingSource implementation requires each page to be of equal size."
        }
        Pager(pagingConfig) {
            NivkhPagingSource(query)
        }
    }

    private fun loadAllWords() {
        mutableState.update {
            NivkhDictionaryState.LoadedState(
                words = defaultPager.flow
            )
        }
    }

    private fun searchWords(query: String) {
        mutableState.update {
            NivkhDictionaryState.LoadedState(
                query = query,
                words = getSearchPager(query).flow
            )
        }
    }
}