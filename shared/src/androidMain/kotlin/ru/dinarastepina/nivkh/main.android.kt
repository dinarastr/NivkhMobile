package ru.dinarastepina.nivkh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toOkioPath


@Composable
fun MainView() {
    val context = LocalContext.current

    CompositionLocalProvider(
        LocalImageLoader provides ImageLoader {
            components {
                setupDefaultComponents(context)
            }
            interceptor {
                //defaultImageResultMemoryCache()
                memoryCacheConfig {
                    maxSizePercent(context, 0.25)
                }
                diskCacheConfig {
                    directory(context.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        },
    ) {
        App()
    }
}