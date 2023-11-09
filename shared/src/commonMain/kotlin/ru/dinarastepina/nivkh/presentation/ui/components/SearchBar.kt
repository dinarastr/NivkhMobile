package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun NivkhSearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    query: MutableState<String>,
    onValueChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNavigateBack: (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        onNavigateBack?.let {
            IconButton(
                onClick = {
                    it.invoke()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        TextField(
            modifier = Modifier.weight(1f).padding(top = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                cursorColor = MaterialTheme.colorScheme.onPrimary
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None
            ),
            maxLines = 1,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            placeholder = {
                Text(hint)
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotBlank()
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            query.value = ""
                            onClearSearch()
                        },
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            },
            value = query.value,
            onValueChange = {
                query.value = it
                if (it.isNotBlank()) {
                    onValueChanged(it)
                } else {
                    onClearSearch()
                }
            }
        )
    }
}