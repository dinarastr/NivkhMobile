package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.models.Phrase

class PhrasesVM: BaseViewModel<PhrasesState, PhrasesEvents>(
    initialState = PhrasesState.Loading
), KoinComponent {

    private val repository: IPhrasesRepository by inject()
    private val mediaPlayerController: MediaPlayerController by inject()
    private val fileManager: FileManager by inject()

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
        coroutineScope.launch(Dispatchers.IO) {
            repository.getPhrasesByTopic(topic).collect { phrases ->
                 mutableState.update {
                        PhrasesState.LoadedPhrases(
                            phrases = phrases,
                            playerController = mediaPlayerController
                        )
                    }
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
        coroutineScope.launch(Dispatchers.IO) {
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
        coroutineScope.cancel()
    }
}