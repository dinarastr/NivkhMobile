package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import ru.dinarastepina.nivkh.presentation.base.State
import ru.dinarastepina.nivkh.presentation.models.Topic

sealed interface TopicsState: State {

    data object Loading: TopicsState
    data class TopicsLoaded(
        val topics: List<Topic> = listOf( Topic("hghgh", "https://firebasestorage.googleapis.com/v0/b/fir-523a0.appspot.com/o/images%2Fcooking%403x.png?alt=media&token=c3a2a5a3-342e-42a4-afed-8e13c2e51559"))
    ): TopicsState
}