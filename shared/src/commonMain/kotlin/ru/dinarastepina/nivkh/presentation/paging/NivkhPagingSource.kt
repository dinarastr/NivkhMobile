package ru.dinarastepina.nivkh.presentation.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultPage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.domain.DictionaryRepository
import ru.dinarastepina.nivkh.presentation.utils.FIRST_PAGE_INDEX
import ru.dinarastepina.nivkh.presentation.utils.PAGE_SIZE

class NivkhPagingSource(
    private val query: String
) : PagingSource<Int, NivkhWord>(), KoinComponent {

    private val repository: DictionaryRepository by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NivkhWord> {
        val page = params.key ?: FIRST_PAGE_INDEX

        val result = if (query.isEmpty()) repository.getNivkhWords(PAGE_SIZE, page)
        else repository.searchNivkhWords(PAGE_SIZE, page, query)
        return PagingSourceLoadResultPage(
            data = result,
            prevKey = (page - PAGE_SIZE).takeIf { it >= FIRST_PAGE_INDEX },
            nextKey = if (result.isNotEmpty()) page + PAGE_SIZE else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, NivkhWord>): Int? = null
}