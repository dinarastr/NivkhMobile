package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import androidx.paging.PagingData
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events
import ru.dinarastepina.nivkh.presentation.paging.NivkhPagingSource
import ru.dinarastepina.nivkh.presentation.paging.RussianPagingSource
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryState

class RussianDictionaryVM: BaseViewModel<RussianDictionaryState>(
    initialState = RussianDictionaryState.LoadedState(
        words = flowOf(PagingData.empty())
    )
) {
    override fun onEvent(event: Events) {
        when (event) {
            is RussianDictionaryEvents.LoadWords -> getAllRussianWords()
            is RussianDictionaryEvents.SearchWords -> searchRussian(event.query)
            is RussianDictionaryEvents.ClearSearch -> getAllRussianWords()
        }
    }

    private val defaultPager: Pager<Int, RussianWord> = kotlin.run {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        check(pagingConfig.pageSize == pagingConfig.initialLoadSize) {
            "As SqlDelight uses offset based pagination, an elegant PagingSource implementation requires each page to be of equal size."
        }
        Pager(pagingConfig) {
            RussianPagingSource("")
        }
    }

    private fun getSearchPager(query: String): Pager<Int, RussianWord> = kotlin.run {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        check(pagingConfig.pageSize == pagingConfig.initialLoadSize) {
            "As SqlDelight uses offset based pagination, an elegant PagingSource implementation requires each page to be of equal size."
        }
        Pager(pagingConfig) {
            RussianPagingSource(query)
        }
    }

    private fun getAllRussianWords() {
        mutableState.update {
            RussianDictionaryState.LoadedState(
                words = defaultPager.flow
            )
        }
    }

    private fun searchRussian(query: String) {
        mutableState.update {
            RussianDictionaryState.LoadedState(
                query = query,
                words = getSearchPager(query).flow
            )
        }
    }
}