package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun KeyBoardDropDown(
    list: List<String>,
    onClick: (String) -> Unit
) {
        Row {
            list.forEach {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                   onClick = { onClick(it) }
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
    }
}