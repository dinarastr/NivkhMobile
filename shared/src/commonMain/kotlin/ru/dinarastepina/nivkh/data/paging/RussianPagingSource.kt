package ru.dinarastepina.nivkh.data.paging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.data.models.RussianWord
import ru.dinarastepina.nivkh.domain.repositories.IDictionaryRepository
import ru.dinarastepina.nivkh.presentation.base.BasePagingSource

class RussianPagingSource(
    override val query: String
) : BasePagingSource<RussianWord>(query), KoinComponent {

    private val repository: IDictionaryRepository by inject()

    override suspend fun loadAllWords(pageSize: Int, offset: Int): List<RussianWord> {
        return repository.getRussianWords(pageSize, offset)
    }

    override suspend fun searchWords(pageSize: Int, offset: Int, query: String): List<RussianWord> {
        return repository.searchRussianWords(pageSize, offset, query)
    }
}