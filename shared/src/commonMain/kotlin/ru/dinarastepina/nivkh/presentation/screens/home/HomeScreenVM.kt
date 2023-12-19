package ru.dinarastepina.nivkh.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dinarastepina.nivkh.domain.repositories.IDataStoreRepository
import ru.dinarastepina.nivkh.presentation.screens.onboarding.OnBoardingScreen

class HomeScreenVM: StateScreenModel<HomeScreenState>(
    initialState = HomeScreenState.InitialScreen
), KoinComponent {

    private val repository: IDataStoreRepository by inject()

    private val _startDestination: MutableState<String> = mutableStateOf("")
    val startDestination: State<String> = _startDestination


    init {
        coroutineScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = TabsScreen.key
                } else {
                    _startDestination.value = OnBoardingScreen.key
                }
            }
        }
    }
}