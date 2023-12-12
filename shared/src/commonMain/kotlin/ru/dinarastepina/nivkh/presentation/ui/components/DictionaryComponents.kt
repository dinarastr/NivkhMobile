package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun LanguageBar(
    startContent: @Composable () -> Unit,
    endContent: @Composable () -> Unit,
    onChangeLanguageClick: () -> Unit
    ) {

    val interactionSource = remember { MutableInteractionSource() }
    val buttonPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        startContent()
        FloatingActionButton(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(40.dp),
            containerColor = if (buttonPressed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.inversePrimary,
            interactionSource = interactionSource,
            onClick = onChangeLanguageClick,
            content = {
                Icon(
                    imageVector = Icons.Default.CompareArrows,
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
            }
        )
        endContent()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LanguageChip(
    languageIcon: String? = null,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        languageIcon?.let {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(it),
                contentDescription = title
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}