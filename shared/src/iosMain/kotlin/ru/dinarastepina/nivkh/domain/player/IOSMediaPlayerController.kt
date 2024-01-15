package ru.dinarastepina.nivkh.domain.player

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerItemNewAccessLogEntryNotification
import platform.AVFoundation.AVPlayerTimeControlStatusPlaying
import platform.AVFoundation.currentItem
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.seekToTime
import platform.AVFoundation.timeControlStatus
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.lastPathComponent
import platform.darwin.NSObjectProtocol
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference


internal actual val playerModule: Module = module {
    singleOf(::IOSMediaPlayerController) bind MediaPlayerController::class
}
class IOSMediaPlayerController : MediaPlayerController {

    private var player: AVPlayer? = null
        set(value) {
            field = value
            checkReady()
        }

    @OptIn(ExperimentalNativeApi::class)
    private var listener: WeakReference<MediaPlayerListener>? = null
    private var endOfAudioObserver: NSObjectProtocol? = null

    private var audioLoadedObserver: NSObjectProtocol? = null

    private fun checkReady() {
        if (player != null) {
            player?.play()
        }
    }

    @OptIn(ExperimentalNativeApi::class)
    override fun prepare(pathSource: String, listener: MediaPlayerListener, cached: Boolean) {
        this.listener = WeakReference(listener)
        val url = if (cached.not()) {
            NSURL(string = pathSource)
        } else {
            pathSource.getFile()!!
        }
        this.player = AVPlayer(
            uRL = url
        )
        if (cached) {
            onReady()
        }
        endOfAudioObserver = NSNotificationCenter.defaultCenter.apply {
            addObserverForName(
                name = AVPlayerItemDidPlayToEndTimeNotification,
                `object` = player?.currentItem,
                queue = NSOperationQueue.mainQueue,
                usingBlock = {
                    listener.onVideoCompleted()
                    player = null
                    release()
                }
            )
        }

        //observer to listen to the moment a url audio is loaded
        audioLoadedObserver = NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemNewAccessLogEntryNotification,
            `object` = this.player?.currentItem,
            queue = NSOperationQueue.mainQueue,
            usingBlock = {
                onReady()
            }
        )
    }

    @OptIn(ExperimentalNativeApi::class)
    private fun onReady() {
        //val playerController = playerController ?: return
        val player = player ?: return

        //playerController.player = player

        listener?.get()?.onReady()
        audioLoadedObserver?.let { NSNotificationCenter.defaultCenter.removeObserver(it) }
    }

    override fun start() {
        //the audio already started
    }

    override fun resume() {
        player?.play()
    }

    override fun pause() {
        player?.pause()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun stop() {
        player?.run {
            pause()
            seekToTime(time = cValue {
                value = 0
            })
        }
    }

    override fun isPlaying(): Boolean {
        return player?.timeControlStatus == AVPlayerTimeControlStatusPlaying
    }

    override fun release() {
        endOfAudioObserver?.let { NSNotificationCenter.defaultCenter.removeObserver(it) }
        audioLoadedObserver?.let { NSNotificationCenter.defaultCenter.removeObserver(it) }
    }

}

fun String.getFile(): NSURL? {
    val fileManager: NSFileManager = NSFileManager.defaultManager()
    val cacheDirectory = fileManager.URLsForDirectory(
        NSDocumentDirectory,
        NSUserDomainMask
    ).first() as NSURL
    val filename = NSURL(string = this).lastPathComponent().orEmpty()
    return cacheDirectory.URLByAppendingPathComponent(filename)
}
