package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import nivkhmobile.shared.generated.resources.Res
import nivkhmobile.shared.generated.resources.ic_arrow_back
import nivkhmobile.shared.generated.resources.ic_clear
import nivkhmobile.shared.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource

@OptIn(FlowPreview::class)
@Composable
fun NivkhSearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    query: MutableState<TextFieldValue>,
    onValueChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNavigateBack: (() -> Unit)? = null,
    debounceTimeMs: Long = 500L
) {
    LaunchedEffect(query) {
        snapshotFlow { query.value.text }
            .debounce(debounceTimeMs)
            .distinctUntilChanged()
            .collect { searchText ->
                if (searchText.isNotBlank()) {
                    onValueChanged(searchText)
                } else {
                    onClearSearch()
                }
            }
    }

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
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null
                )
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
                Icon(painter = painterResource(Res.drawable.ic_search), contentDescription = null)
            },
            placeholder = {
                Text(hint)
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.text.isNotBlank()
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            query.value = TextFieldValue("")
                            onClearSearch()
                        },
                        painter = painterResource(Res.drawable.ic_clear),
                        contentDescription = null
                    )
                }
            },
            value = query.value,
            onValueChange = { newValue ->
                query.value = newValue
            }
        )
    }
}