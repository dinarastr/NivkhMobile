package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import ru.dinarastepina.nivkh.presentation.base.Events

sealed class TopicsEvents: Events {
    data object LoadTopics: TopicsEvents()
}
