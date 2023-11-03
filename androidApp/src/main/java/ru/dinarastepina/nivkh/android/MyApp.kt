package ru.dinarastepina.nivkh.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import ru.dinarastepina.nivkh.di.initKoinForAndroid

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() = initKoinForAndroid(
        appDeclaration = {
            androidLogger()
            androidContext(this@MyApp)
        }
    )
}