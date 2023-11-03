package ru.dinarastepina.nivkh.presentation.screens.dictionary.russian

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import ru.dinarastepina.nivkh.presentation.utils.Tags

object RussianDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.RUSSIAN_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {
        Scaffold {
            Text(text = key)
        }
    }
}