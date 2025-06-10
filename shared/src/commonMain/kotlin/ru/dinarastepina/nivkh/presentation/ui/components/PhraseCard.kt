package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nivkhmobile.shared.generated.resources.Res
import nivkhmobile.shared.generated.resources.ic_cloud_download
import nivkhmobile.shared.generated.resources.ic_pause
import nivkhmobile.shared.generated.resources.ic_play
import nivkhmobile.shared.generated.resources.ic_send
import org.jetbrains.compose.resources.painterResource
import ru.dinarastepina.nivkh.presentation.models.Phrase

@Composable
fun PhraseCard(
    phrase: Phrase,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isPlaying: Boolean = false,
    onPlay: () -> Unit,
    onShare: (Phrase) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        SelectionContainer {
            Column(
                modifier = modifier.background(MaterialTheme.colorScheme.onPrimaryContainer).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = phrase.nivkh,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )

                )
                Divider(
                    modifier = modifier.padding(vertical = 16.dp).background(MaterialTheme.colorScheme.onBackground).fillMaxWidth().height(1.dp))
                Text(
                    text = phrase.russian,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (phrase.audio.isBlank().not()) {
                        IconButton(
                            enabled = isLoading.not(),
                            onClick = {
                            onPlay()
                        }) {
                            Icon(
                                when {
                                    isLoading -> painterResource(Res.drawable.ic_cloud_download)
                                    isPlaying -> painterResource(Res.drawable.ic_pause)
                                    else -> painterResource(Res.drawable.ic_play)
                                },
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "start"
                            )
                        }
                    }
                    IconButton(onClick = {
                        onShare(phrase)
                    }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_send),
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = "start"
                        )
                    }
                }
            }
        }
    }
}