package ru.dinarastepina.nivkh.presentation.base

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultPage
import ru.dinarastepina.nivkh.data.models.Word
import ru.dinarastepina.nivkh.presentation.utils.FIRST_PAGE_INDEX
import ru.dinarastepina.nivkh.presentation.utils.PAGE_SIZE

abstract class BasePagingSource <Z: Word>(
    open val query: String
) : PagingSource<Int, Z>() {

    abstract suspend fun loadAllWords(pageSize: Int, offset: Int): List<Z>

    abstract suspend fun searchWords(pageSize: Int, offset: Int, query: String): List<Z>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Z> {
        val page = params.key ?: FIRST_PAGE_INDEX

        val result = if (query.isEmpty()) loadAllWords(PAGE_SIZE, page)
        else searchWords(PAGE_SIZE, page, query)
        return PagingSourceLoadResultPage(
            data = result,
            prevKey = (page - PAGE_SIZE).takeIf { it >= FIRST_PAGE_INDEX },
            nextKey = if (result.isNotEmpty()) page + PAGE_SIZE else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Z>): Int? = null
}