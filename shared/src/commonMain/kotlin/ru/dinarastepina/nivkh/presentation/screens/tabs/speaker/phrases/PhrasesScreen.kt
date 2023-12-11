package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.ui.progress.AdaptiveCircularProgressIndicator
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.ui.components.PhraseCard
import ru.dinarastepina.nivkh.presentation.utils.Tags

class PhrasesScreen(val topic: String): Screen {

    override val key: ScreenKey = Tags.PHRASES_SCREEN_TITLE.tag
    @Composable
    override fun Content() {
        val phrasesVM = rememberScreenModel { PhrasesVM() }
        val phrasesState by phrasesVM.state.collectAsState()
        val selected = remember { mutableStateOf<Phrase?>(null) }

        LifecycleEffect(
            onStarted = {
                phrasesVM.onEvent(PhrasesEvents.LoadPhrases(topic))
            })

        val navigator = LocalNavigator.currentOrThrow

        OnHomePressed {
            if (phrasesState is PhrasesState.LoadedPhrases && (phrasesState as PhrasesState.LoadedPhrases).playerController.isPlaying()) {
                selected.value = null
                (phrasesState as PhrasesState.LoadedPhrases).playerController.pause()
            }
        }

        BackHandler(
            onBack = {
                if (phrasesState is PhrasesState.LoadedPhrases && (phrasesState as PhrasesState.Success).playerController.isPlaying()) {
                    selected.value = null
                    (phrasesState as PhrasesState.LoadedPhrases).playerController.pause()
                }
                navigator.pop()
            }
        )

        PhrasesList(
            selected = selected,
            state = phrasesState,
            startAudio = {
                phrasesVM.onEvent(PhrasesEvents.StartAudio(it))
            },
            stopAudio = {
                phrasesVM.onEvent(PhrasesEvents.StopAudio)
            },
            checkIfCached = {
                phrasesVM.checkIfCached(it)
            },
            downloadFile = {
                phrasesVM.downloadFile(it)
            },
            onShare = {
                phrasesVM.sharePhrase(it)
            }
        )

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PhrasesList(
        selected: MutableState<Phrase?>,
        state: PhrasesState,
        startAudio: (Phrase) -> Unit,
        stopAudio: () -> Unit,
        checkIfCached: (String) -> Boolean,
        downloadFile: (String) -> Unit,
        onShare: (Phrase) -> Unit
    ) {

        val navigator = LocalNavigator.currentOrThrow


        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = topic,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (state is PhrasesState.LoadedPhrases && state.playerController.isPlaying() ){
                                selected.value = null
                                state.playerController.apply {
                                    stop()
                                    release()
                                }
                            }
                            navigator.pop()
                        })
                        {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is PhrasesState.Loading -> RenderLoading()
                    is PhrasesState.LoadedPhrases -> RenderData(
                        phrases = state.phrases,
                        selected = selected,
                        currentPhrase = state.currentPhrase,
                        player = state.playerController,
                        startAudio = startAudio,
                        stopAudio = stopAudio,
                        checkIfCached = checkIfCached,
                        downloadFile = downloadFile,
                        onShare = onShare
                    )
                }
            }
        }
    }

    private @Composable
    fun RenderLoading() {
        AdaptiveCircularProgressIndicator()
    }

    @Composable
    fun RenderData(
        selected: MutableState<Phrase?>,
        phrases: List<Phrase>,
        player: MediaPlayerController,
        currentPhrase: Phrase?,
        startAudio: (Phrase) -> Unit,
        stopAudio: () -> Unit,
        checkIfCached: (String) -> Boolean,
        downloadFile: (String) -> Unit,
        onShare: (Phrase) -> Unit
    ) {

        val isLoading = remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier.padding(16.dp
            ).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            phrases.forEach { phrase ->
                item (key = phrase.audio){
                    PhraseCard(
                        phrase = phrase,
                        onShare = {
                            onShare(it)
                        },
                        onPlay = {
                            when {
                                isLoading.value -> {
                                    //todo: add snackbar when audio is loading
                                }
                                currentPhrase?.audio == phrase.audio && player.isPlaying() -> {
                                    selected.value = null
                                    player.pause()
                                }
                                currentPhrase?.audio == phrase.audio && player.isPlaying().not() -> {
                                    selected.value = phrase
                                    player.resume()
                                }
                                else -> {
                                    startAudio(phrase)
                                    selected.value = phrase
                                    isLoading.value = true
                                    val cached =
                                        checkIfCached("https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audioUrl}?alt=media")
                                    load(
                                        isLoading = isLoading,
                                        cached = cached,
                                        url = "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audioUrl}?alt=media",
                                        controller = player,
                                        selected = selected
                                    ) {
                                        stopAudio()
                                    }
                                    if (cached.not()) {
                                        downloadFile(
                                            "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audioUrl}?alt=media"
                                        )
                                    }
                                }
                            }
                        },
                        isPlaying = selected.value == phrase && !isLoading.value,
                        isLoading = selected.value == phrase && isLoading.value,
                    )
                }
            }
        }
    }
}


fun load(
    cached: Boolean,
    url: String,
    controller: MediaPlayerController,
    selected: MutableState<Phrase?>,
    isLoading: MutableState<Boolean>,
    onTrackEnded: () -> Unit
) {
    url.let {
        controller.prepare(pathSource = it, cached = cached, listener = object :
            MediaPlayerListener {
            override fun onReady() {
                controller.start()
                isLoading.value = false
            }

            override fun onVideoCompleted() {
                selected.value = null
                onTrackEnded()
                controller.release()
            }

            override fun onError() {

            }

            override fun onAudioLoaded() {
            }
        })
    }
}