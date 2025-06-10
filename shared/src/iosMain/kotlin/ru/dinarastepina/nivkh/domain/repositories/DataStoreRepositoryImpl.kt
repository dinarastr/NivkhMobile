package ru.dinarastepina.nivkh.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

internal actual val dataStoreModule: Module = module {
    singleOf(::DataStoreRepositoryImpl) bind IDataStoreRepository::class
}

class DataStoreRepositoryImpl: IDataStoreRepository {
    
    private companion object {
        const val ON_BOARDING_KEY = "on_boarding_completed"
    }
    
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val _onBoardingState = MutableStateFlow(readOnBoardingStateFromDefaults())
    
    private fun readOnBoardingStateFromDefaults(): Boolean {
        return userDefaults.boolForKey(ON_BOARDING_KEY)
    }
    
    override suspend fun saveOnBoardingState(completed: Boolean) {
        userDefaults.setBool(completed, forKey = ON_BOARDING_KEY)
        userDefaults.synchronize()
        _onBoardingState.value = completed
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return _onBoardingState.asStateFlow()
    }
}