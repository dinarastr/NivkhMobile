package ru.dinarastepina.nivkh.presentation.screens.home

sealed interface HomeScreenState {
    data object InitialScreen: HomeScreenState
}