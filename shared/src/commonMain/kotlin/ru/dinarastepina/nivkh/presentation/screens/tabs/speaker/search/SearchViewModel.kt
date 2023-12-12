package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.search

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.downloader.FileManager
import ru.dinarastepina.nivkh.domain.player.MediaPlayerController
import ru.dinarastepina.nivkh.domain.repositories.IPhrasesRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.models.Phrase

class SearchViewModel : BaseViewModel<SearchState, SearchEvents>(
    SearchState.Empty
), KoinComponent {

    private val repository: IPhrasesRepository by inject()
    private val mediaPlayerController: MediaPlayerController by inject()
    private val fileManager: FileManager by inject()

    override fun onEvent(event: SearchEvents) {
        when (event) {
            is SearchEvents.SearchWords -> searchPhrases(event.query)
            is SearchEvents.StartAudio -> startAudio(
                state = state.value,
                phrase = event.phrase
            )

            is SearchEvents.StopAudio -> stopAudio(
                state = state.value
            )

            is SearchEvents.CheckIfCached -> check(event.url)
            is SearchEvents.DownloadFile -> downloadFile(event.url)
        }
    }

    private fun searchPhrases(query: String) {
        coroutineScope.launch(Dispatchers.IO) {
            val result = repository.searchPhrases(query)
            mutableState.update {
                if (result.isEmpty()) {
                    SearchState.Empty
                } else {
                    SearchState.Success(
                        phrases = result,
                        playerController = mediaPlayerController
                    )
                }
            }
        }
    }

    fun clear() {
        mutableState.update {
            SearchState.Empty
        }
    }

    private fun startAudio(
        state: SearchState,
        phrase: Phrase
    ) {
        if (state is SearchState.Success) {
            mutableState.update {
                state.copy(
                    currentPhrase = phrase
                )
            }
        }
    }

    private fun stopAudio(
        state: SearchState,
    ) {
        if (state is SearchState.Success) {

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

    fun check(url: String): Boolean {
        return fileManager.checkIfFileExists(url)
    }

    override fun onDispose() {
        super.onDispose()
        coroutineScope.cancel()
    }
}