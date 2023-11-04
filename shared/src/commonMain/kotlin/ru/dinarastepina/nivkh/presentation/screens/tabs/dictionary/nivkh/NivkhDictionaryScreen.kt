package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
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

        NivkhDictionaryContent(
            state as NivkhDictionaryState.LoadedState
        )
    }
}

@Composable
fun NivkhDictionaryContent(
    state: NivkhDictionaryState.LoadedState
) {
   val items = state.words.collectAsLazyPagingItems()

   Scaffold {
     LazyColumn()  {
         items(items.itemCount) { position ->
            val word = items[position]
             word?.let {
                 Text(text = it.content.orEmpty())
             }
         }
     }
   }
}
