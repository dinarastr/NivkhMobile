package ru.dinarastepina.nivkh.domain.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

internal actual val playerModule: Module = module {
    singleOf(::AndroidMediaPlayerController) bind MediaPlayerController::class
}
class AndroidMediaPlayerController(private val context: Context): MediaPlayerController {

    private var mediaPlayer: MediaPlayer? = null

    private var isSurfaceReady = false
        set(value) {
            field = value
            if (value.and(isMediaPlayerReady)) onReady()
        }
    private var isMediaPlayerReady = false
        set(value) {
            field = value
            if (value.and(isSurfaceReady)) onReady()
        }

    private lateinit var listener: MediaPlayerListener

    private fun onReady() {
        listener.onReady()
    }


    override fun prepare(pathSource: String, listener: MediaPlayerListener, cached: Boolean) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
        }
        this.listener = listener
        mediaPlayer = MediaPlayer().apply {
            setOnCompletionListener {
                this@AndroidMediaPlayerController.listener.onVideoCompleted()
            }
            setOnPreparedListener { this@AndroidMediaPlayerController.listener.onReady() }
            setOnErrorListener { mediaPlayer, i, i2 ->
                this@AndroidMediaPlayerController.listener.onError()
                mediaPlayer.release()
                true
            }
        }
        if (cached) {
            val uri = pathSource.toUri()
            val file = File(context.cacheDir, "audio/${uri.getFileName()}")
            mediaPlayer?.setDataSource(file.path)
        } else {
            mediaPlayer?.setDataSource(pathSource)
        }
        mediaPlayer?.prepareAsync()
    }

    private fun Uri.getFileName(): String {
        val path = lastPathSegment.orEmpty()
        return if (path.contains("/")) path.split("/").last() else path
    }

    override fun start() {
        mediaPlayer?.start()
    }

    override fun resume() {
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.also { mediaPlayer ->
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun release() {
        isMediaPlayerReady = false
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}