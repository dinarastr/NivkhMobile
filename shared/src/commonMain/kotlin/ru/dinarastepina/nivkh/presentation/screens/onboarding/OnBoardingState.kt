package ru.dinarastepina.nivkh.presentation.screens.onboarding

import ru.dinarastepina.nivkh.presentation.base.State

sealed interface OnBoardingState: State {
    data object OnBoarding: OnBoardingState
}