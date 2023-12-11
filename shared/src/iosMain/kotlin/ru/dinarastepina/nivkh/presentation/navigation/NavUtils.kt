package ru.dinarastepina.nivkh.presentation.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
actual fun BackHandler(onBack: () -> Unit) {}
@Composable
actual fun OnHomePressed(
    onBack: () -> Unit
) {

}