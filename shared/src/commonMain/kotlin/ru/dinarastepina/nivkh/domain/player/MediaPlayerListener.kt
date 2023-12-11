package ru.dinarastepina.nivkh.domain.player

interface MediaPlayerListener {
    fun onReady()
    fun onVideoCompleted()
    fun onError()
    fun onAudioLoaded()
}