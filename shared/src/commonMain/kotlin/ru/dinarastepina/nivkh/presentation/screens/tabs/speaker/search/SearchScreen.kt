package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.dinarastepina.nivkh.domain.player.MediaPlayerController
import ru.dinarastepina.nivkh.presentation.models.Phrase
import ru.dinarastepina.nivkh.presentation.navigation.BackHandler
import ru.dinarastepina.nivkh.presentation.navigation.OnHomePressed
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases.load
import ru.dinarastepina.nivkh.presentation.ui.components.EmptyContainer
import ru.dinarastepina.nivkh.presentation.ui.components.PhraseCard
import ru.dinarastepina.nivkh.presentation.utils.Tags

object SearchScreen : Screen {
    override val key: ScreenKey
        get() = Tags.SEARCH_SCREEN_TITLE.tag

    @Composable
    override fun Content() {
        val searchVM = rememberScreenModel {
            SearchViewModel()
        }
        val searchState by searchVM.state.collectAsState()

        val selected = remember { mutableStateOf<Phrase?>(null) }
        val navigator = LocalNavigator.currentOrThrow

        val query = remember { mutableStateOf(
            when (searchState) {
                is SearchState.Success -> (searchState as SearchState.Success).query
                else -> ""
            }
        ) }

        OnHomePressed {
            if (searchState is SearchState.Success && (searchState as SearchState.Success).playerController.isPlaying()) {
                selected.value = null
                (searchState as SearchState.Success).playerController.pause()
            }
        }

        BackHandler(
            onBack = {
                if (searchState is SearchState.Success && (searchState as SearchState.Success).playerController.isPlaying()) {
                    selected.value = null
                    (searchState as SearchState.Success).playerController.pause()
                }
                navigator.pop()
            }
        )

        SearchResult(
            selected = selected,
            state = searchState,
            startAudio = {
                searchVM.onEvent(SearchEvents.StartAudio(it))
            },
            stopAudio = {
                searchVM.onEvent(SearchEvents.StopAudio)
            },
            checkIfCached = {
                searchVM.check(it)
            },
            downloadFile = {
                searchVM.downloadFile(it)
            },
            query = query,
            onValueChanged = {
                searchVM.onEvent(
                    SearchEvents.SearchWords(query.value)
                )
            },
            onClearSearch = {
                searchVM.clear()
            },
            onShare = {
                searchVM.sharePhrase(it)
            }
        )
    }

    @Composable
    fun SearchResult(
        selected: MutableState<Phrase?>,
        query: MutableState<String>,
        state: SearchState,
        startAudio: (Phrase) -> Unit,
        stopAudio: () -> Unit,
        checkIfCached: (String) -> Boolean,
        downloadFile: (String) -> Unit,
        onValueChanged: (String) -> Unit,
        onClearSearch: () -> Unit,
        onShare: (Phrase) -> Unit
    ) {

        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                UdegeSearchBar(
                    modifier = Modifier.padding(end = 16.dp),
                    onNavigateBack = {
                        if (state is SearchState.Success && state.playerController.isPlaying()) {
                            selected.value = null
                            state.playerController.pause()
                        }
                        navigator.pop()
                    },
                    query = query,
                    onClearSearch = {
                        onClearSearch()
                    },
                    onValueChanged = {
                        onValueChanged(it)
                    },
                    hint = "Введите слово"
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is SearchState.Success -> RenderData(
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

                    is SearchState.Empty -> EmptyContainer()
                }
            }
        }

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
            modifier = Modifier.padding(
                16.dp
            ).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            phrases.forEach { phrase ->
                item(key = phrase.audio) {
                    PhraseCard(
                        phrase = phrase,
                        onShare = {
                            onShare(it)
                        },
                        onPlay = {
                            when {
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
                                        checkIfCached("https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audio}?alt=media")
                                    load(
                                        isLoading = isLoading,
                                        cached = cached,
                                        url = "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audio}?alt=media",
                                        controller = player,
                                        selected = selected
                                    ) {
                                        stopAudio()
                                    }
                                    if (cached.not()) {
                                        downloadFile(
                                            "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/udegeaudio%2F${phrase.audio}?alt=media"
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

@Composable
fun UdegeSearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    query: MutableState<String>,
    onValueChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNavigateBack: (() -> Unit)? = null
    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        onNavigateBack?.let {
            IconButton(
                onClick = {
                    it.invoke()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        TextField(
            modifier = Modifier.weight(1f).padding(top = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                cursorColor = MaterialTheme.colorScheme.onPrimary
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None
            ),
            maxLines = 1,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            placeholder = {
                Text(hint)
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotBlank()
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            query.value = ""
                            onClearSearch()
                        },
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            },
            value = query.value,
            onValueChange = {
                query.value = it
                if (it.isNotBlank()) {
                    onValueChanged(it)
                } else {
                    onClearSearch()
                }
            }
        )
    }
}