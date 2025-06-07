package ru.dinarastepina.nivkh

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ru.dinarastepina.nivkh.presentation.screens.home.HomeScreen
import ru.dinarastepina.nivkh.presentation.ui.theme.MyApplicationTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        AppContent()
    }
}

@Composable
fun AppContent() {
    MyApplicationTheme {
        Navigator(
            screen = HomeScreen
        )
    }
}