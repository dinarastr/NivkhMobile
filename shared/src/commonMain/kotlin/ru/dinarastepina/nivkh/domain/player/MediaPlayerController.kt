package ru.dinarastepina.nivkh.domain.player

import org.koin.core.module.Module

internal expect val playerModule : Module
interface MediaPlayerController {
    fun prepare(pathSource: String, listener: MediaPlayerListener, cached: Boolean = false)

    fun start()

    fun resume()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()
}