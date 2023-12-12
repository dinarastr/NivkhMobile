package ru.dinarastepina.nivkh.domain.downloader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dinarastepina.nivkh.presentation.models.Phrase
import java.io.File
import java.net.URL

internal actual val fileManagerModule: Module = module {
    singleOf(::AndroidFileManager) bind FileManager::class
}
class AndroidFileManager(private val context: Context): FileManager {
    override fun downloadFile(url: String) {
        val input = URL(url).openStream()
        val filename = url.toUri().getFileName()
        File(context.cacheDir, "audio").mkdir()
        val output = File(context.cacheDir, "audio/$filename").outputStream()
        input.use { inputStream ->
            output.use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    private fun Uri.getFileName(): String {
        val path = lastPathSegment.orEmpty()
        return if (path.contains("/")) path.split("/").last() else path
    }

    override fun checkIfFileExists(url: String): Boolean {
        val uri = url.toUri()
        val fileName = uri.getFileName()
        val file = File(
            context.cacheDir, "audio/$fileName"
        )
        println("${file.exists()}")
        return file.exists()
    }

    override fun sharePhrase(phrase: Phrase) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val text = "Нивхский: ${phrase.nivkh}\nРусский: ${phrase.russian}"
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Поделиться фразой").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }
}