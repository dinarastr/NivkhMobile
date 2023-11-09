package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryEvents
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryScreen
import ru.dinarastepina.nivkh.presentation.ui.components.DictionaryContent
import ru.dinarastepina.nivkh.presentation.ui.components.LanguageChip
import ru.dinarastepina.nivkh.presentation.utils.Tags

object RussianDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.RUSSIAN_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {

        val vm = rememberScreenModel { RussianDictionaryVM() }
        val state by vm.state.collectAsState()
        val items = (state as RussianDictionaryState.LoadedState).words.collectAsLazyPagingItems()
        val query = remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow

        LifecycleEffect(
            onStarted = {
                vm.onEvent(
                    RussianDictionaryEvents.LoadWords
                )
            }
        )

        DictionaryContent(
            startLanguageContent = {
                LanguageChip(
                    title = "russian"
                )
            },
            targetLanguageContent = {
                LanguageChip(
                    title = "nivkh"
                )
            },
            onLanguageChange = {
                navigator.replace(NivkhDictionaryScreen)
            },
            onEmptySearch = {
                query.value = ""
                vm.onEvent(
                    RussianDictionaryEvents.ClearSearch
                )
            },
            onSearch = {
                vm.onEvent(
                    RussianDictionaryEvents.SearchWords(it)
                )
            },
            query = query,
            items = items
        )
    }
}