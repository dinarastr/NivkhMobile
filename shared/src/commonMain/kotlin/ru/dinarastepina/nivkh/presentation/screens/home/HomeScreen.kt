package ru.dinarastepina.nivkh.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.DictionaryTab
import ru.dinarastepina.nivkh.presentation.utils.Tags

object HomeScreen: Screen {
    override val key: ScreenKey
        get() = Tags.HOME_SCREEN_TITLE.tag

    @Composable
    override fun Content() {
       TabNavigator(
           tab = DictionaryTab
       ) {
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
                       TabNavigationItem(DictionaryTab)
                   }
               }
           )
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


