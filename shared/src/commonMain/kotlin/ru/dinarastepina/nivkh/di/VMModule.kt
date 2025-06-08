package ru.dinarastepina.nivkh.di

import org.koin.dsl.module
import ru.dinarastepina.nivkh.presentation.screens.home.HomeScreenVM
import ru.dinarastepina.nivkh.presentation.screens.onboarding.OnBoardingVM
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.nivkh.NivkhDictionaryVM
import ru.dinarastepina.nivkh.presentation.screens.tabs.dictionary.russian.RussianDictionaryVM
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases.PhrasesVM
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.search.SearchViewModel
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics.TopicsVM

val viewModelsModule = module {
    factory { HomeScreenVM(get()) }
    factory { OnBoardingVM(get()) }
    factory { NivkhDictionaryVM() }
    factory { RussianDictionaryVM() }
    factory { PhrasesVM(get(), get(), get()) }
    factory { SearchViewModel(get(), get(), get()) }
    factory { TopicsVM(get()) }
}