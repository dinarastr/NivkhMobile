package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

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
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian.RussianDictionaryScreen
import ru.dinarastepina.nivkh.presentation.ui.components.DictionaryContent
import ru.dinarastepina.nivkh.presentation.ui.components.LanguageChip
import ru.dinarastepina.nivkh.presentation.utils.Tags

object NivkhDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.NIVKH_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {
        val vm = rememberScreenModel { NivkhDictionaryVM() }
        val state by vm.state.collectAsState()

        val query = remember { mutableStateOf("") }

        val navigator = LocalNavigator.currentOrThrow

        val items = (state as NivkhDictionaryState.LoadedState).words.collectAsLazyPagingItems()

        LifecycleEffect(
            onStarted = {
                vm.onEvent(
                    NivkhDictionaryEvents.LoadWords
                )
            }
        )

        DictionaryContent(
            startLanguageContent = {
                LanguageChip(
                    title = "нивхский"
                )
            },
            targetLanguageContent = {
                LanguageChip(
                    title = "русский"
                )
            },
            onLanguageChange = {
              navigator.replace(RussianDictionaryScreen)
            },
            onEmptySearch = {
                query.value = ""
                            vm.onEvent(
                                NivkhDictionaryEvents.ClearSearch
                            )
            },
            onSearch = {
                       vm.onEvent(
                           NivkhDictionaryEvents.SearchWords(it)
                       )
            },
            query = query,
            items = items
        )
    }
}
