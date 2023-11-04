package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh

import ru.dinarastepina.nivkh.presentation.base.Events

sealed class NivkhDictionaryEvents: Events {
    data object LoadWords: NivkhDictionaryEvents()

    data class SearchWords(val query: String): NivkhDictionaryEvents()
}