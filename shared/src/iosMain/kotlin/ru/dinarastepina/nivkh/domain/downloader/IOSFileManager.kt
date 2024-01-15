package ru.dinarastepina.nivkh.domain.downloader

import kotlinx.cinterop.BetaInteropApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.create
import platform.Foundation.lastPathComponent
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.popoverPresentationController
import ru.dinarastepina.nivkh.presentation.models.Phrase

internal actual val fileManagerModule: Module = module {
    singleOf(::IOSFileManager) bind FileManager::class
}
class IOSFileManager: FileManager {
    @OptIn(BetaInteropApi::class)
    override fun downloadFile(url: String) {
        val fileManager: NSFileManager = NSFileManager.defaultManager()
        val nsUrl = NSURL(string = url)
        val file = NSData.create(contentsOfURL = nsUrl)
        val cacheDirectory = fileManager.URLsForDirectory(
            NSDocumentDirectory,
            NSUserDomainMask
        ).first()
        val filename = (cacheDirectory as NSURL).URLByAppendingPathComponent(NSURL(string = url).lastPathComponent().orEmpty())
        fileManager.createFileAtPath(filename?.path.orEmpty(), file,null)
    }

    override fun checkIfFileExists(url: String): Boolean {
        val fileManager: NSFileManager = NSFileManager.defaultManager()
        val name = NSURL(string = url).lastPathComponent().orEmpty()
        val cacheDirectory = fileManager.URLsForDirectory(
            NSDocumentDirectory,
            NSUserDomainMask
        ).first()
        val path = (cacheDirectory as NSURL).URLByAppendingPathComponent(pathComponent = name)?.path.orEmpty()
        return fileManager.fileExistsAtPath(path)
    }

    override fun sharePhrase(phrase: Phrase) {
        val sharePicker = UIActivityViewController(
            activityItems = listOf(
                "Нивхский: ${phrase.nivkh}\nРусский: ${phrase.russian}"
            ),
            applicationActivities = null
        )
        val window = UIApplication.sharedApplication.windows().first() as UIWindow?
        sharePicker.popoverPresentationController()?.sourceView = window
        sharePicker.setTitle("Поделиться фразой")
        window?.rootViewController?.presentViewController(
            sharePicker as UIViewController,
            animated = true,
            completion = null
        )
    }
}
