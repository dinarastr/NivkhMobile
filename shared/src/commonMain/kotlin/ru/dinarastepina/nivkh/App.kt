package ru.dinarastepina.nivkh

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import ru.dinarastepina.nivkh.presentation.screens.home.HomeScreen
import ru.dinarastepina.nivkh.presentation.ui.theme.MyApplicationTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25) // 25% of available memory
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("image_cache"))
                    .maxSizeBytes(512L * 1024 * 1024) // 512MB
                    .build()
            }
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }
    KoinContext {
        AppContent()
    }
}

@Composable
fun AppContent() {
    MyApplicationTheme {
        Navigator(
            screen = HomeScreen
        )
    }
}