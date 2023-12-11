package ru.dinarastepina.nivkh.presentation.screens.onboarding

import ru.dinarastepina.nivkh.presentation.base.Events

sealed class OnBoardingEvents : Events{
    data object FinishEvent: OnBoardingEvents()
}
