package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import ru.dinarastepina.nivkh.presentation.utils.Tags

class PhrasesScreen(val topic: String): Screen {

    override val key: ScreenKey = Tags.PHRASES_SCREEN_TITLE.tag
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}