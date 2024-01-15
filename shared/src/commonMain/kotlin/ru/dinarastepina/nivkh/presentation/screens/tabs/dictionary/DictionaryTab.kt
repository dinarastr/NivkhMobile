package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryScreen
import ru.dinarastepina.nivkh.presentation.utils.Tags

internal class DictionaryTab: Tab {

    override val key: ScreenKey = Tags.DICTIONARY_SCREEN_TITLE.tag

    override val options: TabOptions
        @Composable
        get() {
            val title = "Словарь"
            val icon = rememberVectorPainter(Icons.Default.Abc)

            return remember {
                TabOptions(
                    index = 0u,
                    title,
                    icon = icon
                )
            }
        }
    @Composable
    override fun Content() {
        Navigator(
            screen = NivkhDictionaryScreen
        ) {
            CurrentScreen()
        }
    }
}
