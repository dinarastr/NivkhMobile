package ru.dinarastepina.nivkh.presentation.screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import ru.dinarastepina.nivkh.presentation.utils.Tags

object HomeScreen: Screen {
    override val key: ScreenKey
        get() = Tags.HOME_SCREEN_TITLE.tag

    @Composable
    override fun Content() {
       Scaffold {
           Text("hghghghgh")
       }
    }
}