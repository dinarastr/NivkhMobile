package ru.dinarastepina.nivkh

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ru.dinarastepina.nivkh.presentation.screens.home.HomeScreen
import ru.dinarastepina.nivkh.presentation.ui.MyApplicationTheme

@Composable
fun App() {
    MyApplicationTheme {
        Navigator(
            screen = HomeScreen
        )
    }
}