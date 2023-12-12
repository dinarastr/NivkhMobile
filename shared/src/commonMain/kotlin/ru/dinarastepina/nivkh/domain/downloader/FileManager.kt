package ru.dinarastepina.nivkh.domain.downloader

import org.koin.core.module.Module
import ru.dinarastepina.nivkh.presentation.models.Phrase

internal expect val fileManagerModule: Module

interface FileManager {
    fun downloadFile(url: String = "https://file-examples.com/storage/fee3d1095964bab199aee29/2017/11/file_example_WAV_1MG.wav")

    fun checkIfFileExists(url: String): Boolean

    fun sharePhrase(phrase: Phrase)
}