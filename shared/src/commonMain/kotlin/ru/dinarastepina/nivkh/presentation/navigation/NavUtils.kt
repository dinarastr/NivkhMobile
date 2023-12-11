package ru.dinarastepina.nivkh.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
expect fun BackHandler(onBack: () -> Unit)

@Composable
expect fun OnHomePressed(
    onBack: () -> Unit
)