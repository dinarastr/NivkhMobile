package ru.dinarastepina.nivkh.data.paging

import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.domain.repositories.IDictionaryRepository
import ru.dinarastepina.nivkh.presentation.base.BasePagingSource

class RussianPagingSource(
    override val query: String,
    private val repository: IDictionaryRepository
) : BasePagingSource<RussianWord>(query) {

    override suspend fun loadAllWords(pageSize: Int, offset: Int): List<RussianWord> {
        return repository.getRussianWords(pageSize, offset)
    }

    override suspend fun searchWords(pageSize: Int, offset: Int, query: String): List<RussianWord> {
        return repository.searchRussianWords(pageSize, offset, query)
    }
}