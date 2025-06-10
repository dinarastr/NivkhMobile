package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import ru.dinarastepina.nivkh.presentation.models.Phrase

/**
 * Stable wrapper for Phrase to prevent unnecessary recompositions
 */
@Stable
data class StablePhrase(val phrase: Phrase)

/**
 * Optimized phrase selection state using derivedStateOf
 */
@Composable
fun rememberPhraseSelectionState(
    currentPhrase: Phrase?,
    targetPhrase: Phrase,
    isPlaying: Boolean,
    isLoading: Boolean
): PhraseSelectionState {
    return remember(currentPhrase?.audio, targetPhrase.audio, isPlaying, isLoading) {
        val isCurrentPhrase = currentPhrase?.audio == targetPhrase.audio
        PhraseSelectionState(
            isCurrentPhrase = isCurrentPhrase,
            isPlaying = isCurrentPhrase && isPlaying,
            isLoading = isCurrentPhrase && isLoading,
            canPlay = !isLoading
        )
    }
}

@Stable
data class PhraseSelectionState(
    val isCurrentPhrase: Boolean,
    val isPlaying: Boolean,
    val isLoading: Boolean,
    val canPlay: Boolean
)

/**
 * Optimized search state to prevent recomposition on every character
 */
@Composable
fun rememberSearchState(query: String): SearchUIState {
    val hasQuery by remember { derivedStateOf { query.isNotBlank() } }
    val shouldShowClear by remember { derivedStateOf { query.isNotEmpty() } }
    
    return remember(hasQuery, shouldShowClear) {
        SearchUIState(
            hasQuery = hasQuery,
            shouldShowClear = shouldShowClear
        )
    }
}

@Stable
data class SearchUIState(
    val hasQuery: Boolean,
    val shouldShowClear: Boolean
) 