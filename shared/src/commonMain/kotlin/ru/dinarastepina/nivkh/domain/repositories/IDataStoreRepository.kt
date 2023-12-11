package ru.dinarastepina.nivkh.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

internal expect val dataStoreModule : Module
interface IDataStoreRepository {
    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>
}