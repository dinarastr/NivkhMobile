package ru.dinarastepina.nivkh.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.dinarastepina.nivkh.presentation.screens.onboarding.OnBoardingScreen
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.DictionaryTab
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.SpeakerTab
import ru.dinarastepina.nivkh.presentation.ui.components.InfoDialog
import ru.dinarastepina.nivkh.presentation.utils.Tags

object HomeScreen: Screen {
    override val key: ScreenKey
        get() = Tags.HOME_SCREEN_TITLE.tag

    @Composable
    override fun Content() {

        val vm = rememberScreenModel { HomeScreenVM() }


        val homeState by vm.startDestination

        when (homeState) {
            OnBoardingScreen.key -> {
                Navigator(
                    screen = OnBoardingScreen
                ) {
                    Scaffold {
                        CurrentScreen()
                    }
                }
            }
            TabsScreen.key -> {
                TabsContent()
            }
        }
    }
}

@Composable
fun CurrentTab(
    modifier: Modifier
) {
    val tabNavigator = LocalTabNavigator.current
    val currentTab = tabNavigator.current

    tabNavigator.saveableState("currentTab") {
        Box(modifier = modifier) {
            currentTab.Content()
        }
    }
}

@Composable
fun RowScope.InfoItem(
    onClick: () -> Unit
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.secondary
        ),
        selected = false,
        onClick = onClick,
        icon = {
            Icon(Icons.Default.Info, contentDescription = null)
        },
        label = {
            Text(
                text = "О приложении",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
private fun RowScope.TabNavigationItem(
    tab: Tab
) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.secondary
        ),
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

object TabsScreen: Screen {
    override val key: ScreenKey
        get() = Tags.TABS_SCREEN_TITLE.tag

    @Composable
    override fun Content() {
        TabsContent()
    }
}

@Composable
fun TabsContent() {
    TabNavigator(
        tab = DictionaryTab()
    ) {

        var open by remember { mutableStateOf(false) }

        if (open) {
            InfoDialog {
                open = it
            }
        }

        Scaffold(
            content = { padding ->
                CurrentTab(
                    modifier = Modifier.padding(
                        bottom = padding.calculateBottomPadding()
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    TabNavigationItem(DictionaryTab())
                    TabNavigationItem(SpeakerTab())
                    InfoItem {
                        open = true
                    }
                }
            }
        )
    }
}


