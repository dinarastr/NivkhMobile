package ru.dinarastepina.nivkh.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class Topic(
    val title: String,
    val imageUrl: String
)

@Immutable
data class Phrase(
    val nivkh: String,
    val russian: String,
    val audio: String,
    val topic: String
)
