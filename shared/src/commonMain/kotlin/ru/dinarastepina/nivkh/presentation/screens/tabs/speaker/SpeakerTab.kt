package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SpatialAudio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics.TopicsScreen
import ru.dinarastepina.nivkh.presentation.utils.Tags

object SpeakerTab: Tab {

    override val key: ScreenKey = Tags.SPEAKER_SCREEN_TITLE.tag
    @Composable
    override fun Content() {
        Navigator(
            screen = TopicsScreen
        ) {
            CurrentScreen()
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Разговорник"
            val icon = rememberVectorPainter(Icons.Default.SpatialAudio)

            return remember {
                TabOptions(
                    index = 1u,
                    title,
                    icon = icon
                )
            }
        }
}