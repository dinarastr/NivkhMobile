package ru.dinarastepina.nivkh.presentation.screens.onboarding

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import ru.dinarastepina.nivkh.domain.repositories.IDataStoreRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel

class OnBoardingVM(
    private val repository: IDataStoreRepository
): BaseViewModel<OnBoardingState, OnBoardingEvents>(
    initialState = OnBoardingState.OnBoarding
) {

    override fun onEvent(event: OnBoardingEvents) {
        when (event) {
            is OnBoardingEvents.FinishEvent -> {
                finishOnBoarding()
            }
        }
    }

    private fun finishOnBoarding() {
        screenModelScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingState(true)
        }
    }
}