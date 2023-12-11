package ru.dinarastepina.nivkh.presentation.screens.onboarding

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.repositories.IDataStoreRepository
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel

class OnBoardingVM: BaseViewModel<OnBoardingState, OnBoardingEvents>(
    initialState = OnBoardingState.OnBoarding
), KoinComponent {

    private val repository: IDataStoreRepository by inject()


    override fun onEvent(event: OnBoardingEvents) {
        when (event) {
            is OnBoardingEvents.FinishEvent -> {
                finishOnBoarding()
            }
        }
    }

    private fun finishOnBoarding() {
        coroutineScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingState(true)
        }
    }
}