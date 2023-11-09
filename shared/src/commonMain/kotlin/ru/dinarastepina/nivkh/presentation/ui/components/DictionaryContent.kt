package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import ru.dinarastepina.nivkh.presentation.models.Article

@Composable
fun DictionaryContent(
    startLanguageContent: @Composable () -> Unit,
    targetLanguageContent: @Composable () -> Unit,
    items: LazyPagingItems<Article>,
    onLanguageChange: () -> Unit,
    onSearch: (String) -> Unit,
    onEmptySearch: () -> Unit,
    query: MutableState<String>
) {
    Scaffold(
        topBar = {
            LanguageBar(
                startContent = startLanguageContent,
                endContent = targetLanguageContent,
                onChangeLanguageClick = onLanguageChange
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(top = padding.calculateTopPadding())
        ) {
            NivkhSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                query = query,
                onClearSearch = onEmptySearch,
                onValueChanged = {
                    query.value = it
                    onSearch(it)
                },
                hint = "Введите слово"
            )
            LazyColumn(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
            ) {
                items(items.itemCount) { position ->
                    val word = items[position]
                    word?.let {
                        Text(text = it.content)
                    }
                }
            }
        }
    }
}