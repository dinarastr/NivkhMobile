package ru.dinarastepina.nivkh.data.paging

import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.domain.repositories.IDictionaryRepository
import ru.dinarastepina.nivkh.presentation.base.BasePagingSource

class NivkhPagingSource(
    override val query: String,
    private val repository: IDictionaryRepository
) : BasePagingSource<NivkhWord>(query) {
    override suspend fun loadAllWords(pageSize: Int, offset: Int): List<NivkhWord> {
        return repository.getNivkhWords(pageSize, offset)
    }

    override suspend fun searchWords(pageSize: Int, offset: Int, query: String): List<NivkhWord> {
        return repository.searchNivkhWords(pageSize, offset, query)
    }
}