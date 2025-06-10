package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import nivkhmobile.shared.generated.resources.Res
import nivkhmobile.shared.generated.resources.ic_headphones
import org.jetbrains.compose.resources.painterResource
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics.TopicsScreen
import ru.dinarastepina.nivkh.presentation.utils.Tags

internal class SpeakerTab: Tab {

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
            val icon = painterResource(Res.drawable.ic_headphones)

            return remember {
                TabOptions(
                    index = 1u,
                    title,
                    icon = icon
                )
            }
        }
}
