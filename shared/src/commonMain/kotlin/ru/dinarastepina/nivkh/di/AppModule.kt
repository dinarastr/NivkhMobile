package ru.dinarastepina.nivkh.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

fun appModule() = module {
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
}