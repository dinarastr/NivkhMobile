package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import nivkhmobile.shared.generated.resources.Res
import nivkhmobile.shared.generated.resources.ic_dictionary
import org.jetbrains.compose.resources.painterResource
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryScreen
import ru.dinarastepina.nivkh.presentation.utils.Tags

internal class DictionaryTab: Tab {

    override val key: ScreenKey = Tags.DICTIONARY_SCREEN_TITLE.tag

    override val options: TabOptions
        @Composable
        get() {
            val title = "Словарь"
            val icon = painterResource(Res.drawable.ic_dictionary)

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
