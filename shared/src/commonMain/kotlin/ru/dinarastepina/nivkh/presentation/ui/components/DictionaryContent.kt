package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.cash.paging.compose.LazyPagingItems
import ru.dinarastepina.nivkh.presentation.models.Article

@Composable
fun DictionaryContent(
    items: LazyPagingItems<Article>
) {
    Scaffold {
        LazyColumn()  {
            items(items.itemCount) { position ->
                val word = items[position]
                word?.let {
                    Text(text = it.content)
                }
            }
        }
    }
}