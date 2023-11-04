package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

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

object NivkhDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.NIVKH_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {
        val vm = rememberScreenModel { NivkhDictionaryVM() }
        val state by vm.state.collectAsState()

        LifecycleEffect(
            onStarted = {
                vm.onEvent(
                    NivkhDictionaryEvents.LoadWords
                )
            }
        )

        DictionaryContent(
            items = (state as NivkhDictionaryState.LoadedState).words.collectAsLazyPagingItems()
        )
    }
}
