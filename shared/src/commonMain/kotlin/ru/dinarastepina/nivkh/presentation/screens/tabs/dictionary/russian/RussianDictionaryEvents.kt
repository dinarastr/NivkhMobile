package ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian

import ru.dinarastepina.nivkh.presentation.base.Events
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryEvents

sealed class RussianDictionaryEvents: Events {
    data object LoadWords: RussianDictionaryEvents()

    data class SearchWords(val query: String): RussianDictionaryEvents()
}
