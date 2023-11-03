package ru.dinarastepina.nivkh.presentation.screens.dictionary.nivkh

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import ru.dinarastepina.nivkh.presentation.utils.Tags

object NivkhDictionaryScreen: Screen {
    override val key: ScreenKey
        get() = Tags.NIVKH_DICTIONARY_TITLE.tag

    @Composable
    override fun Content() {
        Scaffold {
            Text(text = key)
        }
    }
}