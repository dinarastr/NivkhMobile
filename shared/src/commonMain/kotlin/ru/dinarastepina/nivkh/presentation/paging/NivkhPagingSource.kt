package ru.dinarastepina.nivkh.presentation.paging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.domain.IDictionaryRepository
import ru.dinarastepina.nivkh.presentation.base.BasePagingSource

class NivkhPagingSource(
    override val query: String
) : BasePagingSource<NivkhWord>(query), KoinComponent {

    private val repository: IDictionaryRepository by inject()
    override suspend fun loadAllWords(pageSize: Int, offset: Int): List<NivkhWord> {
        return repository.getNivkhWords(pageSize, offset)
    }

    override suspend fun searchWords(pageSize: Int, offset: Int, query: String): List<NivkhWord> {
        return repository.searchNivkhWords(pageSize, offset, query)
    }
}