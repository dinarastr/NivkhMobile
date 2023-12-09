package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases

import org.koin.core.component.KoinComponent
import ru.dinarastepina.nivkh.presentation.base.BaseViewModel
import ru.dinarastepina.nivkh.presentation.base.Events

class PhrasesVM: BaseViewModel<PhrasesState>(
    initialState = PhrasesState.LoadedPhrases()
), KoinComponent {
    override fun onEvent(event: Events) {
        TODO("Not yet implemented")
    }
}