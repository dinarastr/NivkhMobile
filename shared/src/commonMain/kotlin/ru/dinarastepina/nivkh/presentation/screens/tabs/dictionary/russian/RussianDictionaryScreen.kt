package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import ru.dinarastepina.nivkh.presentation.ui.components.DictionaryContent
import ru.dinarastepina.nivkh.presentation.utils.Tags

object RussianDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.RUSSIAN_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {

        val vm = rememberScreenModel { RussianDictionaryVM() }
        val state by vm.state.collectAsState()

        LifecycleEffect(
            onStarted = {
                vm.onEvent(
                    RussianDictionaryEvents.LoadWords
                )
            }
        )

        DictionaryContent(
            items = (state as RussianDictionaryState.LoadedState).words.collectAsLazyPagingItems()
        )
    }
}