package ru.dinarastepina.nivkh.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val dataStoreModule: Module = module {
    singleOf(::DataStoreRepositoryImpl) bind IDataStoreRepository::class
}
class DataStoreRepositoryImpl: IDataStoreRepository {
    override suspend fun saveOnBoardingState(completed: Boolean) {

    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return flow { emit(true) }
    }
}