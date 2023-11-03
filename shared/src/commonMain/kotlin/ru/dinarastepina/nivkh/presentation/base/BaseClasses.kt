package ru.dinarastepina.nivkh.presentation.base

import cafe.adriel.voyager.core.model.StateScreenModel

/* Helper base entities for the presentation layer
 */
interface Events

interface State

abstract class BaseViewModel<T: State>(initialState: T): StateScreenModel<T>(initialState) {
    abstract fun onEvent(event: Events)
}