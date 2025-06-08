package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dinarastepina.nivkh.domain.downloader.FileManager
import ru.dinarastepina.nivkh.domain.player.MediaPlayerController
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.models.Phrase

class PhrasesVM(
    private val repository: IPhrasesRepository,
    private val mediaPlayerController: MediaPlayerController,
    private val fileManager: FileManager
): BaseViewModel<PhrasesState, PhrasesEvents>(
    initialState = PhrasesState.Loading
) {

    override fun onEvent(event: PhrasesEvents) {
        when (event) {
            is PhrasesEvents.LoadPhrases -> loadPhrases(event.topic)
            is PhrasesEvents.StartAudio -> startAudio(
                state = state.value,
                phrase = event.phrase
            )
            is PhrasesEvents.StopAudio -> stopAudio(
                state = state.value
            )
            is PhrasesEvents.CheckIfCached -> checkIfCached(event.url)
            is PhrasesEvents.DownloadFile -> downloadFile(event.url)
        }
    }

    private fun loadPhrases(topic: String) {
        screenModelScope.launch(Dispatchers.IO) {
                 mutableState.update {
                        PhrasesState.LoadedPhrases(
                            phrases = repository.getPhrasesByTopic(topic),
                            playerController = mediaPlayerController
                        )
                    }
        }
    }

    private fun startAudio(
        state: PhrasesState,
        phrase: Phrase
    ) {
        if (state is PhrasesState.LoadedPhrases) {
            mutableState.update {
                state.copy(
                    currentPhrase = phrase
                )
            }
        }
    }

    private fun stopAudio(
        state: PhrasesState,
    ) {
        if (state is PhrasesState.LoadedPhrases) {

            mutableState.update {
                state.copy(
                    currentPhrase = null
                )
            }
        }
    }

    fun downloadFile(url: String) {
        screenModelScope.launch(Dispatchers.IO) {
            fileManager.downloadFile(url)
        }
    }

    fun sharePhrase(phrase: Phrase) {
        fileManager.sharePhrase(phrase)
    }

    fun checkIfCached(url: String): Boolean {
        return fileManager.checkIfFileExists(url)
    }

    override fun onDispose() {
        super.onDispose()
        screenModelScope.cancel()
    }
}